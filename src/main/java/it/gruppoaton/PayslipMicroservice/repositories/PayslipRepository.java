package it.gruppoaton.PayslipMicroservice.repositories;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.entities.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Integer> {

    @Query("select p from Payslip p where p.employee = ?1")
    List<Payslip> findByEmployee(Employee employee);
}
