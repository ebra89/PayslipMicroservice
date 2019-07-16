package it.gruppoaton.PayslipMicroservice.controller;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<Employee>employees = new ArrayList<>();
        try {
            employees = employeeService.getAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }


    @GetMapping("/findByName")
    public List<Employee> employeeByNameLastName(@RequestParam("name") String name){
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }

    @GetMapping("/{fiscalCode}")
    public Employee employeeByFiscalCode(@PathVariable ("fiscalCode") String fiscalCode){
        Employee employee = null;
        try {
            employee = employeeService.findByFc(fiscalCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return employee;
    }


}
