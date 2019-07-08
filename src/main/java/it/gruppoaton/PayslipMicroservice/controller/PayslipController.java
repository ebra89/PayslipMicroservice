package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Payslip;
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

import java.io.FileNotFoundException;
import java.util.List;

//@RestController
@RequestMapping("/payslips")
//@CrossOrigin(origins = {"http://localhost:4200"})
@Controller
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
        return payslipService.findEmployeePayslips(employeeService.findByFc(fiscalCode));
    }

    @GetMapping("/{sixMonthPayslip}")
    public List<Payslip> sixMonthPayslip(@PathVariable ("sixMonthPayslip") String sixMonthPayslip, Payslip payslip){
        return payslipService.showLastSixMonthsPayslips(payslip);
    }

    @GetMapping("/{byMonth}")
    public Payslip payslipByMonth(@PathVariable ("payslipByMonth") String payslipByMonth, int month){
        return payslipService.findByMonth(month);
    }

    @GetMapping("/{byYear}")
    public Payslip payslipByYear(@PathVariable ("payslipByYear") String payslipByYear, int year){
        return payslipService.findByYear(year);
    }



    @GetMapping("/downloadPayslip/{payslipId}")
    public ResponseEntity<Resource> downloadPayslip(@PathVariable Integer payslipId) throws FileNotFoundException {

        Payslip payslip = payslipService.getPayslip(payslipId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(payslip.getTypeFile()))
                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ payslip.getEmployee().getFiscalCode() + "\"")
                        .body(new ByteArrayResource(payslip.getPayslipPdf()));

    }


}
