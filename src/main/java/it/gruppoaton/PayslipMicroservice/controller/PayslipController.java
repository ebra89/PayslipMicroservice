package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.model.PayslipModel;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/payslips")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public List<Payslip> payslips(){
        return payslipService.getAll();
    }

    @GetMapping("/{fiscalCode}")
    public List<Payslip> employeePayslips(@PathVariable("fiscalCode") String fiscalCode){
        System.out.println("fiscal code "+fiscalCode);
        return payslipService.findEmployeePayslips(employeeService.findByFc(fiscalCode));
    }

    @GetMapping("/sixMonthPayslip/{fiscalCode}")
    public List<Payslip> sixMonthPayslip(@PathVariable("fiscalCode") String fiscalCode){
        return payslipService.showLastSixMonthsPayslips(fiscalCode);
    }
 /*
    @GetMapping("/{byMonth}")
    public Payslip payslipByMonth(@PathVariable ("payslipByMonth") String payslipByMonth, int month){
        return payslipService.findByMonth(month);
    }

    @GetMapping("/{byYear}")
    public Payslip payslipByYear(@PathVariable ("payslipByYear") String payslipByYear, int year){
        return payslipService.findByYear(year);
    }

  */

    @GetMapping(value = "/downloadPayslip/{payslipId}", produces = "application/pdf")
    public ResponseEntity<Resource> downloadPayslip(@PathVariable Integer payslipId) {

        Payslip payslip = null;
        try {
            payslip = payslipService.getFile(payslipId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("payslip id: "+payslipId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ payslip.getEmployee().getFiscalCode() +".pdf"+ "\"")
                        .body(new ByteArrayResource(payslip.getPayslipPdf()));



    }


}
