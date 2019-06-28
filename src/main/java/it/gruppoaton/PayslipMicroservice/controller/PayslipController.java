package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    public List<Payslip> listaPayslip(String fc){
        List<Payslip> list = payslipService.findByFiscalCode(fc);
        System.out.println(list);
        return payslipService.findByFiscalCode(fc);
    }

}
