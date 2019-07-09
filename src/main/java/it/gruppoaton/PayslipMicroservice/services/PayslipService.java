package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.component.EmailService;
import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.model.Email;
import it.gruppoaton.PayslipMicroservice.repositories.PayslipRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    public Email storePayslip (String path) throws FileNotFoundException {


            File file = new File(path);
            String fileName =  file.getName();
            String fiscalCode = fileName.substring(fileName.length()- 20, fileName.length()- 4);

            byte fileContent [] = new byte[(int)file.length()];
            FileInputStream fis = null;

            int n=0;
            int month=0;
            int year=0;
            String[] tokens = StringUtils.split(fileName,"_-.");
            LinkedList<Integer> i=new LinkedList<>();

            for(String s : tokens){
                try {
                    i.add(Integer.parseInt(s));
                }catch (Exception ex){
                    System.out.println(ex.getStackTrace());
                }
                n++;
            }
            month = i.get(0);
            year = i.get(1);

            try {
                fis = new FileInputStream(file);
                fis.read(fileContent);
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Employee employee = employeeService.findByFc(fiscalCode);
            Payslip payslip = new Payslip(fileContent,month,year,employee);
            payslipRepository.save(payslip);
            Email email = new Email(employee, "nuovo cedolino","hai un nuovo cedolino!");
            return email;

    }

    public Payslip getFile(Integer id) throws FileNotFoundException {
        return payslipRepository.findById(id)
                .orElseThrow(()-> new FileNotFoundException("file not found "));
    }


                                                                 // mi trova payslip con una ricerca su employee,mese ,anno e lo aggiorna
    public void updatePayslip(Payslip payslip){
        //Employee employee = employeeService.findOne(fiscalCode);
        //int month = payslip.getMonth();
        //int year = payslip.getYear();
        //payslip = payslipRepository.findByEMA(employee,month,year);
        payslip = payslipRepository.findByEMA(payslip.getEmployee(),payslip.getMonth(),payslip.getYear());
        payslipRepository.save(payslip);
    }

                                                                         // trova tutti payslip del employye in questione
    public List<Payslip>findEmployeePayslips(Employee employee){
        return payslipRepository.findByEmployee(employee);
    }

                                                                            // trova payslip con una ricerca su mese
    public Payslip findByMonth(int month) {

        return payslipRepository.findByMonth(month);
    }
                                                                            // trova payslip con una ricerca su anno
    public Payslip findByYear(int year){

        return payslipRepository.findByYear(year);
    }

    public List<Payslip>showLastSixMonthsPayslips(String fiscalCode){
        System.out.println("fiscal code da service "+fiscalCode);
        List<Payslip> lastPayslips = new ArrayList<>();

        List<Payslip> payslips = payslipRepository.findByEmployee(employeeService.findByFc(fiscalCode));
        for (Payslip p: payslips ) {

            int month = p.getMonth();
            String m;
            if (month<10){
                m="0"+month;
            }else{m=""+month;}
            int year = p.getYear();
            System.out.println("month: "+m+""+"year "+year+"");
            String date = "01"+"/" + m+"/" + year;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate payslipDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();
            LocalDate sixMonthsBefore = LocalDate.now().minusDays(182);

            if(payslipDate.isAfter(sixMonthsBefore)){
                lastPayslips.add(p);
            }
        }
    	return lastPayslips;
    }


    public List<Payslip> getAll() {

        return payslipRepository.findAll();
    }

    public Payslip getPayslip(Integer payslipId) {

        return payslipRepository.getOne(payslipId);
    }



}