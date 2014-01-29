package br.com.cowabunga.integration;

import static org.junit.Assert.*;

import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.cowabunga.AbstractTests;
import br.com.cowabunga.domain.Employee;

@ContextConfiguration({"classpath:spring/dataTestsContext.xml","classpath:spring/applicationContext.xml"})
@Transactional(propagation=Propagation.REQUIRED)
public class AbstractIntegrationTests extends AbstractTests {

	@Override
	protected String getFileName() {
		return "sql/prepareIntegrationTestsDataBase.sql";
	}

	protected void assertEmployee(Long id, Employee expectedEmployee) {
		Map<String, Object> persistedEmployee = jdbcTemplate.queryForMap("SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?", id);
		
		assertEquals( expectedEmployee.getFirstName(), persistedEmployee.get("FIRST_NAME") );
		assertEquals( expectedEmployee.getLastName(), persistedEmployee.get("LAST_NAME") );
		assertEquals( expectedEmployee.getRole(), persistedEmployee.get("ROLE") );
		assertEquals( expectedEmployee.getSalary(), persistedEmployee.get("SALARY") );
	}
}
