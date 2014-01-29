package br.com.cowabunga.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.cowabunga.domain.Employee;

public class EmployeeMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		
		employee.setId( rs.getLong("EMPLOYEE_ID") );
		employee.setFirstName( rs.getString("FIRST_NAME") );
		employee.setLastName( rs.getString("LAST_NAME") );
		employee.setRole( rs.getString("ROLE") );
		employee.setSalary( rs.getBigDecimal("SALARY") );
		
		return employee;
	}

}
