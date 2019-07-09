package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = {"http://localhost:4200"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayslipService payslipService;


    @GetMapping("/list")
    public List<Employee>employees(){

        return employeeService.getAll();

    }


    @GetMapping("/{nameLastName}")
    public List<Employee> employeeByNameLastName(@PathVariable("nameLastName") String nameLastName){
        return employeeService.findByName(nameLastName);
    }

    @GetMapping("/{fiscalCode}")
    public Employee employeeByFiscalCode(@PathVariable ("fiscalCode") String fiscalCode, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("Utente con questo codice fiscale non esiste");
        }
        return employeeService.findByFc(fiscalCode);
    }


}
