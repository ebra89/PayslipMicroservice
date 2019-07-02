package it.gruppoaton.PayslipMicroservice;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayslipMicroserviceApplicationTests {


	@Autowired
	private EmployeeService employeeService;

	@Test
	public void contextLoads() {
	}

	@Before
	public void initDb() {
		Employee employee1 = new Employee("2345678", "pippo", "pappo", "pippo@gmail.com");
		employeeService.createEmployee(employee1);

		Employee employee2 = new Employee("23456876", "giggio", "giggi", "giggio@gmail.com");
		employeeService.createEmployee(employee2);





		String prova = "ebrahim_fazeli_04_2019_123456789123423w";

		String month = StringUtils.substringBetween(prova,"_","_");
		//if(StringUtils.isNumeric(month)){
			System.out.println(month);
		//}else{
		//	StringUtils.
		//}
		int n=1;
		int mese=0;
		int anno=0;
		String[] seq = StringUtils.split(prova,"_");
		for (String s:seq) {

			if (n==3){
				mese=Integer.parseInt(s);
			}
			if (n==4){
				anno=Integer.parseInt(s);
			}
			System.out.println(s);
			n++;
		}
		System.out.println("mese "+ mese + " anno"+anno);

		LinkedList<String> list = new LinkedList<>();

		System.out.println(seq);

	}
	Path pathFile = Paths.get("/home/ebrasupertramp/prova.pdf");




}
