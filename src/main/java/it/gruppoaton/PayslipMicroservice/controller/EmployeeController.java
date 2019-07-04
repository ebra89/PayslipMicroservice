package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayslipService payslipService;


    @GetMapping("/list")
    public String employeeList(Model model){
        model.addAttribute("employees",employeeService.employeeList());
        return "employeeListPage";
    }


    @GetMapping("/employeesByName")
    public String findByName(Model model, @RequestParam(defaultValue = "") String firstName){
        model.addAttribute("employees",employeeService.findByName(firstName));
        return "employeeListPage";
    }


}
