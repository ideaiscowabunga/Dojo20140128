package br.com.cowabunga.json;

import java.io.Serializable;
import java.util.List;

import br.com.cowabunga.domain.ValidationError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("serial")
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class ErrorJson implements Serializable {

	private String errorMessage;
	private String cause;
	private List<ValidationError> validationErrors;

	public ErrorJson(Exception e) {
		this.errorMessage = e.getMessage();
		
		if( e.getCause() != null ) {
			this.cause = e.getCause().getMessage();
		}
	}
	
	public ErrorJson(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorJson(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getCause() {
		return cause;
	}
	
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}
	
	public void setValidationErrors(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
