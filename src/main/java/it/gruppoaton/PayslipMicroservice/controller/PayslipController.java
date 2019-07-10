package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
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
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    public List<PayslipModel> payslips(){
        return payslipService.getAll();
    }

    @GetMapping("/{fiscalCode}")
    public List<PayslipModel> employeePayslips(@PathVariable("fiscalCode") String fiscalCode){
        List<PayslipModel> payslips = new ArrayList<>();
        try{
            payslips = payslipService.findEmployeePayslips(employeeService.findByFc(fiscalCode));
        }catch (Exception e){
            System.out.println("Nessun utente con questo codice fiscale");
        }
        return payslips;
    }

    @GetMapping("/sixMonthPayslip/{fiscalCode}")
    public List<Payslip> sixMonthPayslip(@PathVariable("fiscalCode") String fiscalCode){
        List<Payslip> payslips = new ArrayList<>();
        try {
            payslips = payslipService.showLastSixMonthsPayslips(fiscalCode);
        }catch (Exception e){
            System.out.println("Nessun utente con questo codice fiscale");
        }
        return payslips;
    }

    @GetMapping("/byDate/{fiscalCode}")
    public List<PayslipModel> payslipByMonth(@RequestParam("month") int month, @RequestParam("year") int year,@PathVariable("fiscalCode") String fiscalCode){
        List<PayslipModel>payslips = new ArrayList<>();
        Employee employee = null;
        try {
            employee = employeeService.findByFc(fiscalCode);
        }catch (Exception e){
            System.out.println("Nessun utente con questo codice fiscale");
        }try {
            payslips = payslipService.payslipEYM(month,year,employee);
        }catch (Exception e){
            System.out.println("mese o anno non sono corretti");
        }
        return payslips;
    }





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
