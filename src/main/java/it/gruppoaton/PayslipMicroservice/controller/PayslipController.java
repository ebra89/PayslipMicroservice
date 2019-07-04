package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/payslips")
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String listPayslips(Model model){
        List<Payslip> payslips = new ArrayList<Payslip>();
        payslips=payslipService.payslipsList();
        model.addAttribute("payslips", payslips);
        return "payslip";
    }
    @GetMapping("/{fiscalCode}")
    public String listPayslipsEmployee(Model model, @PathVariable("fiscalCode") String fiscalCode){
        List<Payslip> payslips = new ArrayList<Payslip>();
        payslips=payslipService.findEmployeePayslips(employeeService.findByFc(fiscalCode));
        model.addAttribute("payslips", payslips);
        return "payslip";
    }

}
