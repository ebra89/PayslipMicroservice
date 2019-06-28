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

    @GetMapping("/list")
    public String listPayslip(Model model, String fiscalCode){

        Employee employee = employeeService.findOne(fiscalCode);
        model.addAttribute("payslips", payslipService.findEmployeePayslips(employee));
        System.out.println("ciao!!");
        return "/home";
    }

}
