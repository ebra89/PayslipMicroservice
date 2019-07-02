package it.gruppoaton.PayslipMicroservice;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayslipMicroserviceApplicationTests {


	@Autowired
	private EmployeeService employeeService;

	@Test
	public void contextLoads() {
	}

	@Before
	public void initDb(){
		Employee employee1 = new Employee("2345678","pippo","pappo","pippo@gmail.com");
		employeeService.createEmployee(employee1);

		Employee employee2 = new Employee("23456876","giggio","giggi","giggio@gmail.com");
		employeeService.createEmployee(employee2);


	}



}
