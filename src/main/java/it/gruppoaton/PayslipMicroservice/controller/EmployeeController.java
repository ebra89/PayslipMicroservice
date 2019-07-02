package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayslipService payslipService;

    @GetMapping("/employeeList")
    public String employeeList(Model model){
        model.addAttribute("employees",employeeService.employeeList());
        //System.out.println(employeeService.employeeList());
        //System.out.println(employeeService.findOne("2345678"));
        return "employeeListPage";
    }

    @GetMapping("/employeeFindOne")
    public String findOne(Model model,String firstName,String lastName){
        model.addAttribute("employee",employeeService.findOne(firstName,lastName));
        System.out.println(employeeService.findOne("pippo","pappo"));
        return "employeeListPage";
    }
}
