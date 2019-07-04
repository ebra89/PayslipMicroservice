package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findOne(String firstName, String lastName){
        return employeeRepository.findOne(firstName,lastName);
    }

    public Employee findByFc(String fiscalCode){

        return employeeRepository.findOneByFC(fiscalCode);
    }

    public void createEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public List<Employee> employeeList(){

        return employeeRepository.findAll();
    }
}
