package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.repositories.EmployeeRepository;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeService employeeService;

    public List<Payslip> listaPayslip(String fiscalCode){

        Employee employee = employeeService.findOne(fiscalCode);
        System.out.println(payslipService.findEmployeePayslips(employee));
        return payslipService.findEmployeePayslips(employee);
    }

}
