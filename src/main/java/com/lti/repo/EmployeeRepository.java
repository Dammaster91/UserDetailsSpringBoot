package com.lti.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}