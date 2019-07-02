package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.repositories.PayslipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

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

    public Payslip storePayslip (File file)throws IOException{

                                    // da provare!!
            byte[] fileByte = file.getPath().getBytes();
            String fileName =  file.getName();
            String fiscalCode = fileName.substring(fileName.length()- 16);
           // Employee employee = employeeService.findOne(fiscalCode);

           // Payslip payslipFile = new Payslip(1,employee,fileByte,6,2019);
            //return payslipRepository.save(payslipFile);
        return null;
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
    public Payslip findByMonth(int month){
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
    	List<Payslip> lastPayslips = new ArrayList<Payslip>();

    	while (payslipDate.isAfter(sixMonthsBefore)&&payslipDate.isBefore(currentDate)) {
    		
    		lastPayslips.add(payslip);
    		
    	}
    	
    	return lastPayslips;
    }
    		
    	
    }


