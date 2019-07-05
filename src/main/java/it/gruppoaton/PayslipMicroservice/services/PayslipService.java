package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import it.gruppoaton.PayslipMicroservice.component.EmailService;
import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.model.Email;
import it.gruppoaton.PayslipMicroservice.repositories.PayslipRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    public void storePayslip (String path, Buffer buffer) throws FileNotFoundException {

        //TODO
        // da provare!!

            File file = new File(path);
            String fileName =  file.getName();
            String fiscalCode = fileName.substring(fileName.length()- 20, fileName.length()- 4);

            byte fileContent [] = new byte[(int)file.length()];
            FileInputStream fis = null;

            int n=1;
            int month=0;
            int year=0;
            String[] fileNameSep = StringUtils.split(fileName,"_");

            try {
                for (String s : fileNameSep){
                    if(n == 3){
                        month = Integer.parseInt(s);
                    }
                    if(n == 4){
                        year = Integer.parseInt(s);
                    }
                    n++;
                }
            }catch (NumberFormatException ex){
                System.out.println(ex.toString());

            }finally {
                //todo
            }
            try{
                fis = new FileInputStream(file);
                fis.read(fileContent);

            }catch (FileNotFoundException ex){
                throw new FileNotFoundException();
            }catch (IOException e) {
                e.printStackTrace();
            }finally {

                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        Employee employee = employeeService.findByFc(fiscalCode);
        Payslip payslip = new Payslip(fileContent,month,year,employee);
        payslipRepository.save(payslip);
        Email email=new Email(employee, " oggetto", "nuovo cedolino caricato" );
        buffer.putEmail(email);

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

    public List<Payslip>showLastSixMonthsPayslips(Payslip payslip){

    	int month = payslip.getMonth();
    	int year = payslip.getYear();

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    	String date = "01" + month + year;
    	LocalDate payslipDate = LocalDate.parse(date, formatter);
    	LocalDate currentDate = LocalDate.now();
    	LocalDate sixMonthsBefore = LocalDate.now().minusDays(182);
    	List<Payslip> lastPayslips = new ArrayList<>();

    	while (payslipDate.isAfter(sixMonthsBefore)&&payslipDate.isBefore(currentDate)) {

    		lastPayslips.add(payslip);
    	}
    	return lastPayslips;
        }
        public List<Payslip> payslipsList() {
            return payslipRepository.findAll();


        }

    }