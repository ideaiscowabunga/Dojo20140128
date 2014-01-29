package br.com.cowabunga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.cowabunga.dao.EmployeeDao;
import br.com.cowabunga.domain.Employee;
import br.com.cowabunga.exception.PersistenceException;
import br.com.cowabunga.exception.ValidateException;
import br.com.cowabunga.validator.EmployeeValidator;

public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmployeeValidator employeeValidator;

	public Employee findById(String employeeId) throws PersistenceException {
		try {
			return employeeDao.findById( parseId(employeeId) );
		} catch (ValidateException e) {
			throw new EmptyResultDataAccessException(employeeId+" nao e um id valido", 1);
		}
	}

	private Long parseId(String employeeId) throws ValidateException {
		try {
			return Long.valueOf(employeeId);
		} catch ( NumberFormatException e ) {
			throw new ValidateException(employeeId+" nao e um id valido", e);
		}
	}

	public List<Employee> findAll() throws PersistenceException {
		return employeeDao.findAll();
	}

	public Employee create(Employee employee) throws ValidateException, PersistenceException {
		employeeValidator.validate( employee );

		employee.setId( employeeDao.getNextEmployeeId() );
		employeeDao.create(employee);
		return employee;
	}
	
	public void delete(String employeeId) throws PersistenceException, ValidateException {
		employeeDao.delete( parseId(employeeId) );
	}
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}
	
	public void setEmployeeValidator(EmployeeValidator employeeValidator) {
		this.employeeValidator = employeeValidator;
	}
	
	public EmployeeValidator getEmployeeValidator() {
		return employeeValidator;
	}
}
