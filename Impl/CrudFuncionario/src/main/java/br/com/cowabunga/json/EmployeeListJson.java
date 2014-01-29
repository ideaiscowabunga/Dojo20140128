package br.com.cowabunga.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.cowabunga.domain.Employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("serial")
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class EmployeeListJson extends ArrayList<EmployeeJson> implements Serializable {

	public EmployeeListJson(List<Employee> employees) {
		if( employees != null && !employees.isEmpty() ) {
			for( Employee employee : employees ) {
				add( new EmployeeJson(employee) );
			}
		}
	}
	
}