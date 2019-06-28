package it.gruppoaton.PayslipMicroservice.repositories;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {


    @Query("select e from Employee e where e.fiscalCode = ?1")
    Employee findOne(String fiscalCode);
}
