package br.com.cowabunga.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cowabunga.domain.Employee;
import br.com.cowabunga.domain.ValidationError;
import br.com.cowabunga.exception.ValidateException;

public class EmployeeValidator {

	private Boolean isInvalid;
	private Map<String, String> validationErrors;
	
	public EmployeeValidator() {
		clearState();
	}
	
	public void validate(Employee employee) throws ValidateException {
		clearState();
		
		isBlank( employee.getFirstName() ).addReason("firstName", "Campo obrigatório.");
		isBlank( employee.getLastName() ).addReason("lastName", "Campo obrigatório.");
		isBlank( employee.getRole() ).addReason("role", "Campo obrigatório.");
		isNull( employee.getSalary() ).addReason("salary", "Campo obrigatório.");
		
		isNotLenghtBetween(employee.getFirstName(), 1, 50).addReason("firstName", "O campo deve ter entre 1 e 15 caracteres.");
		isNotLenghtBetween(employee.getLastName(), 1, 50).addReason("lastName", "O campo deve ter entre 1 e 15 caracteres.");
		isNotLenghtBetween(employee.getRole(), 1, 50).addReason("role", "O campo deve ter entre 1 e 15 caracteres.");
		isNotBetween(employee.getSalary(), BigDecimal.ZERO, BigDecimal.valueOf( 999999.99d ) ).addReason("salary", "O salário deve ser entre R$0,01 e R$999.999,99");
		
		if( !validationErrors.isEmpty() ) {
			throw new ValidateException( getValidationErrorsAsList() );
		}
	}

	private List<ValidationError> getValidationErrorsAsList() {
		
		List<ValidationError> validationErrorList = new ArrayList<ValidationError>();
		for( String key : validationErrors.keySet() ) {
			validationErrorList.add( new ValidationError(key, validationErrors.get(key)) );
		}
		
		return validationErrorList;
	}

	private EmployeeValidator isNotBetween(BigDecimal fieldValue, BigDecimal minSize, BigDecimal maxSize) {
		if( fieldValue == null ) {
			return this;
		}
		
		isInvalid = fieldValue.compareTo(minSize) <= 0 || fieldValue.compareTo(maxSize) > 0; 
		return this;
	}

	private EmployeeValidator isNotLenghtBetween(String fieldValue, int minSize, int maxSize) {
		if( fieldValue == null || fieldValue.isEmpty() ) {
			return this;
		}
		
		isInvalid = (fieldValue.length() < minSize || fieldValue.length() > maxSize);
		return this;
	}

	private EmployeeValidator isNull(Object fieldValue) {
		isInvalid = (fieldValue == null);
		return this;
	}

	private EmployeeValidator isBlank(String fieldValue) {
		isInvalid = (fieldValue == null || fieldValue.isEmpty());
		return this;
	}

	private void addReason(String fieldName, String errorMessage) {
		if( isInvalid ) {
			validationErrors.put(fieldName, errorMessage);
		}
	}

	private void clearState() {
		isInvalid = Boolean.FALSE;
		validationErrors = new HashMap<String, String>();
	}

}
