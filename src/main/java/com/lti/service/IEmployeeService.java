package com.lti.service;

import java.util.List;
import java.util.Optional;

import com.lti.model.Employee;

public interface IEmployeeService {
	public Integer saveEmployee(Employee e);

	public void updateEmployee(Employee e);

	public void deleteEmployee(Integer id);

	public Optional<Employee> getOneEmployee(Integer id);

	public List<Employee> getAllEmployees();

	public boolean isPresent(Integer id);
}