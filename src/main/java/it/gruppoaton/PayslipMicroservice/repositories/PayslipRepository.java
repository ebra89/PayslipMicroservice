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

    @Query("select p from Payslip p where p.month = ?1")
    Payslip findByMonth(int month);

    @Query("select p from Payslip p where p.year = ?1")
    Payslip findByYear(int year);

    @Query("select p from Payslip p where p.employee = ?1 and p.month = ?2 and p.year = ?3")
    Payslip findByEMA(Employee employee, int month, int year);


}
