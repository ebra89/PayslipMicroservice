package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.repositories.EmployeeRepository;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/lista")
    public String listaPayslip(Model model, String firstName,String cognome){

        Employee employee = employeeService.findOne(firstName,cognome);
        System.out.println(payslipService.findEmployeePayslips(employee));
        payslipService.findEmployeePayslips(employee);
        System.out.println(employeeService.findByFc("1234567890123456"));

        return "home";
    }

}
