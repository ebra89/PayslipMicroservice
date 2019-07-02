package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.repositories.PayslipRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeService employeeService;

    public Payslip storePayslip (File file){

        //TODO
                                                                                        // da provare!!
            byte[] fileByte = file.getPath().getBytes();
            String fileName =  file.getName();
            String fiscalCode = fileName.substring(fileName.length()- 16);
            Employee employee = employeeService.findByFc(fiscalCode);

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
                throw new  NumberFormatException();

            }catch (NumberFormatException ex){
                System.out.println(ex.toString());

           }finally {
                //todo
            }
            Payslip payslip = new Payslip(fileByte,month,year,employee);


            return payslipRepository.save(payslip);

    }

                                                                     // aggiunge un payslip a quell employee
    public void addPayslip(Payslip payslip, Employee employee){
        payslip.setEmployee(employee);
        payslipRepository.save(payslip);
    }
    //public Payslip findPayslip(Payslip payslip, int month, int year, String fiscalCode){
    //    return payslipRepository.findByEMA(payslip.getEmployee(),payslip.getMonth(),payslip.getYear());
    //}


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

}
