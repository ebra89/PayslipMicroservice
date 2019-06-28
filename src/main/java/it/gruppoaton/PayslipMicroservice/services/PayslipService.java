package it.gruppoaton.PayslipMicroservice.services;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import it.gruppoaton.PayslipMicroservice.repositories.EmployeeRepository;
import it.gruppoaton.PayslipMicroservice.repositories.PayslipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void savePayslip(Payslip payslip){
        payslipRepository.save(payslip);
    }

    public List<Payslip>findEmployeePayslips(Employee employee){
        return payslipRepository.findByEmployee(employee);
    }

}
