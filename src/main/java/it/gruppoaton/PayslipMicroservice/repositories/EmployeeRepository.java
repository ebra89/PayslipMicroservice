package it.gruppoaton.PayslipMicroservice.repositories;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {


    @Query("select e from Employee e where e.firstName = ?1 and e.lastName =?2")
    Employee findOne(String firstName, String lastName);

    @Query("select e from Employee e where e.fiscalCode = ?1")
    Employee findOneByFC(String fiscalCode);


}
