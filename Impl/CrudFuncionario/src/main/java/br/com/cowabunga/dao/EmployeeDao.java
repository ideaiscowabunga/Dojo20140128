package br.com.cowabunga.dao;

import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.cowabunga.dao.rowmapper.EmployeeMapper;
import br.com.cowabunga.domain.Employee;
import br.com.cowabunga.exception.PersistenceException;

public class EmployeeDao {

	private ResourceBundle resourceBundle = ResourceBundle.getBundle("sql/dml");
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Long getNextEmployeeId() throws PersistenceException {
		try {
			String sql = getDml("select.next.employee.id");
			return jdbcTemplate.queryForLong(sql);
		} catch(Exception e) {
			throw new PersistenceException("Ocorreu um erro ao buscar um novo id de funcionario", e);
		}
	}
	
	public void create(Employee employee) throws PersistenceException {
		try {
			String sql = getDml("insert.employee");
			jdbcTemplate.update(sql, employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getSalary(), employee.getRole());
		} catch(Exception e) {
			throw new PersistenceException("Ocorreu um erro ao salvar o funcionario", e);
		}
	}
	
	public Employee findById(Long id) throws PersistenceException {
		try {
			String sql = getDml("select.employee.by.id");
			Employee employee = jdbcTemplate.queryForObject(sql, new EmployeeMapper(), id);
			return employee;
		} catch(EmptyResultDataAccessException e) {
			throw e;
		} catch(Exception e) {
			throw new PersistenceException("Ocorreu um erro ao buscar o funcionario de id ["+id+"]", e);
		}
	}
	
	public List<Employee> findAll() throws PersistenceException {
		try {
			String sql = getDml("select.all.employees");
			List<Employee> employees = jdbcTemplate.query(sql, new EmployeeMapper());
			return employees;
		} catch(Exception e) {
			throw new PersistenceException("Ocorreu um erro ao buscar os funcionarios", e);
		}
	}
	
	public void delete(Long id) throws PersistenceException {
		try {
			String sql = getDml("delete.employee");
			jdbcTemplate.update(sql, id);
		} catch(Exception e) {
			throw new PersistenceException("Ocorreu um erro ao buscar o funcionario de id ["+id+"]", e);
		}
	}

	private String getDml(String key) {
		return resourceBundle.getString(key);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
