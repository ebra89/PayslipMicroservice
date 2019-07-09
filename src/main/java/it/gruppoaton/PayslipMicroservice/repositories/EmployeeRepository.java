package it.gruppoaton.PayslipMicroservice.repositories;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {


    @Query("select e from Employee e where UPPER (e.firstName) like UPPER (?1) or UPPER (e.lastName) like UPPER (?1) or UPPER (e.fiscalCode) like UPPER (?1)")
    List<Employee> findByNameLike(String firstName);

    @Query("select e from Employee e where UPPER (e.fiscalCode) = ?1")
    Employee findOneByFC(String fiscalCode);

}
