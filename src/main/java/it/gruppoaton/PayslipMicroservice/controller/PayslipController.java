package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/payslip")
    public String listaPayslip(Model model){
        List<Payslip> payslips = new ArrayList<Payslip>();
        //Employee employee = employeeService.findOne(firstName,cognome);
        //payslips = payslipService.findEmployeePayslips(employee);
        payslips=payslipService.payslipsList();
        model.addAttribute("payslips", payslips);
        return "payslip";
    }

}
