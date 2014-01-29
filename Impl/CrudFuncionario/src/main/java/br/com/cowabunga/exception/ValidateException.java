package br.com.cowabunga.exception;

import java.util.List;

import br.com.cowabunga.domain.ValidationError;

@SuppressWarnings("serial")
public class ValidateException extends Exception {

	private List<ValidationError> validationErrors;

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(List<ValidationError> validationErrors) {
		super( validationErrors.toString() );
		this.validationErrors = validationErrors;
	}
	
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}
	
}
