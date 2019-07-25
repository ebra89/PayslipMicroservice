package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.component.EmailService;
import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.model.Email;
import it.gruppoaton.PayslipMicroservice.model.PayslipConverter;
import it.gruppoaton.PayslipMicroservice.model.PayslipModel;
import it.gruppoaton.PayslipMicroservice.repositories.PayslipRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    final static Logger logger = Logger.getLogger(PayslipService.class);

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    PayslipConverter payslipConverter;

    public Email storePayslip (Path path) throws FileNotFoundException {


            File file = new File(path.toString());
            String fileName =  file.getName();
            String fiscalCode = fileName.substring(fileName.length()- 20, fileName.length()- 4);




            int month=0;
            int year=0;
            int version=0;


            String[] tokens = StringUtils.split(fileName,"_-.");
            LinkedList<Integer> i=new LinkedList<>();

            int n=0;
            for(String s : tokens){
                try {
                    i.add(Integer.parseInt(s));
                }catch (Exception ex){
                    //System.out.println(ex.getStackTrace());
                }
                n++;
            }
            month = i.get(0);
            year = i.get(1);
            if(i.size()>=3){
                version=i.get(2);
            }


            String type;
            String[] p= StringUtils.split(path.getParent().toString(),"\\ /");
            type = p[p.length-1];




            byte fileContent [] = new byte[(int)file.length()];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                fis.read(fileContent);
            }catch (FileNotFoundException ex){
                //ex.printStackTrace();
            }catch (IOException e){
                //e.printStackTrace();
            }finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }

            Employee employee = null;
            try {
                employee = employeeService.findByFc(fiscalCode);
            }catch (Exception e){
                System.out.println("Nessun utente con questo codice fiscale");
            }

            Payslip payslip;
            List<Payslip> payslipsFind=payslipRepository.isExsit(employee,month,year, version, type);
            if (!payslipsFind.isEmpty()){
                Payslip payslipExisting=payslipsFind.get(0);
                payslipExisting.setPayslipPdf(fileContent);
                payslip=payslipExisting;
            }else{
                payslip = new Payslip(fileContent, month, year, employee, version, type);
            }



            try {
                payslipRepository.save(payslip);
                logger.info("hai salvato un nuovo cedolino! "+fileName);
            }catch (Exception e){
                logger.error("salvataggio su db non riuscito!! "+fileName);
                return null;
            }
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            //Email email = new Email(employee, "Nuovo cedolino"," Gentile "+firstName.toUpperCase()+" "+lastName.toUpperCase()+" hai un nuovo cedolino.");
            Email email = Email.createEmail(employee, type);
            return email;

    }

    public Payslip getFile(Integer id) throws FileNotFoundException {
        return payslipRepository.findById(id)
                .orElseThrow(()-> new FileNotFoundException("file not found "));
    }


                                                                 // mi trova payslip con una ricerca su employee,mese ,anno e lo aggiorna
    public List<PayslipModel> payslipEYM (int month, int year, Employee employee){

       return payslipConverter.toViewModelList(payslipRepository.findByEMA(employee,month,year));


    }

                                                                         // trova tutti payslip del employye in questione
    public List<PayslipModel>findEmployeePayslips(Employee employee){

        return payslipConverter.toViewModelList(payslipRepository.findByEmployee(employee));
    }

                                                                            // trova payslip con una ricerca su mese
    public Payslip findByMonth(int month) {

        return payslipRepository.findByMonth(month);
    }
                                                                            // trova payslip con una ricerca su anno
    public Payslip findByYear(int year){

        return payslipRepository.findByYear(year);
    }

    public List<PayslipModel>showLastSixMonthsPayslips(String fiscalCode){
        List<Payslip> lastPayslips = new ArrayList<>();
        List<Payslip> payslips = payslipRepository.findByEmployee(employeeService.findByFc(fiscalCode));
        for (Payslip p: payslips ) {

            int month = p.getMonth();
            String m;
            if (month<10){
                m ="0"+month;
            }else{
                m =" "+month;
            }
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
    	return payslipConverter.toViewModelList(lastPayslips);
    }

    public List<PayslipModel> getAll() {

        return payslipConverter.toViewModelList(payslipRepository.findAll());
    }

    public Payslip getPayslip(Integer payslipId) {

        return payslipRepository.getOne(payslipId);
    }


}