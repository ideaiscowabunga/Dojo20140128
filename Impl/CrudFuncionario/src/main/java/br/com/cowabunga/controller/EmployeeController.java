package br.com.cowabunga.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cowabunga.domain.Employee;
import br.com.cowabunga.exception.ValidateException;
import br.com.cowabunga.json.EmployeeJson;
import br.com.cowabunga.json.EmployeeListJson;
import br.com.cowabunga.json.ErrorJson;
import br.com.cowabunga.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);
	
	@RequestMapping(method = RequestMethod.GET, value="/{employeeId}")
	public @ResponseBody Serializable get(@PathVariable("employeeId") String employeeId, HttpServletResponse response) {
		try {
			Employee employee = employeeService.findById( employeeId );
			return new EmployeeJson(employee);
		} catch (EmptyResultDataAccessException e) {
			response.setStatus( HttpStatus.NOT_FOUND.value() );
			return new ErrorJson( "employee ["+employeeId+"] not found" );
		} catch (Exception e) {
			LOGGER.error("Deu ruim, manolo...", e);
			response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
			return new ErrorJson( e );
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Serializable list(HttpServletResponse response) {
		try {
			List<Employee> employees = employeeService.findAll();
			return new EmployeeListJson( employees );
		} catch (Exception e) {
			LOGGER.error("Deu ruim, manolo...", e);
			response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
			return new ErrorJson( e );
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Serializable create( @RequestBody EmployeeJson request, HttpServletResponse response) {
		try {
			Employee employee = employeeService.create( request.toDomain() );
			return new EmployeeJson(employee);
		} catch( ValidateException e ) {
			response.setStatus( HttpStatus.UNPROCESSABLE_ENTITY.value() );
			return new ErrorJson( e.getValidationErrors() );
		} catch( Exception e ) {
			LOGGER.error("Deu ruim, manolo...", e);
			response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
			return new ErrorJson( e );
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{employeeId}")
	public @ResponseBody Serializable delete(@PathVariable("employeeId") String employeeId, HttpServletResponse response) {
		try {
			employeeService.delete(employeeId);
			return "";
		} catch (ValidateException e) {
			response.setStatus( HttpStatus.NOT_FOUND.value() );
			return new ErrorJson( e );
		} catch (Exception e) {
			LOGGER.error("Deu ruim, manolo...", e);
			response.setStatus( HttpStatus.NOT_FOUND.value() );
			return new ErrorJson( e );
		}
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody Serializable handleValidation(HttpMessageNotReadableException e, HttpServletResponse response) {
		LOGGER.error("Ocorreu um erro ao bindar o JSON.", e);
		response.setStatus( HttpStatus.BAD_REQUEST.value() );
        return new ErrorJson( "formato de dados invalido" );
    }
	
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
}