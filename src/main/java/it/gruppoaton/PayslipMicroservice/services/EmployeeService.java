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


    public List<Employee> findByName(String firstName){
        return employeeRepository.findByNameLike("%"+firstName+"%");
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
