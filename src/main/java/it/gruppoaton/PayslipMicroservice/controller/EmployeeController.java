package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.gruppoaton.PayslipMicroservice.model.Email;
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    Buffer buffer= Buffer.getInstance();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayslipService payslipService;


    @GetMapping("/list")
    public String employeeList(Model model){
        model.addAttribute("employees",employeeService.employeeList());
        System.out.println("PIPPO");
        System.out.println(buffer.takeCoda());
        for (Email e: buffer.takeCoda()) {

            System.out.println("EMAIL BUFFER "+ e.getEmployee().getEmail());

        }

        return "employeeListPage";
    }


    @GetMapping("/employeesByName")
    public String findByName(Model model, @RequestParam(defaultValue = "") String firstName){
        model.addAttribute("employees",employeeService.findByName(firstName));
        return "employeeListPage";
    }


}
