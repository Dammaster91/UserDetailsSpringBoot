package com.lti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.Employee;
import com.lti.service.IEmployeeService;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeController {
	@Autowired
	private IEmployeeService service;

	// 1. save Employee data
	// http://localhost:9090/rest/employee/save
	// @PostMapping("/save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody Employee employee) {
		ResponseEntity<String> resp = null;
		try {
			Integer id = service.saveEmployee(employee);
			resp = new ResponseEntity<String>("Employee '" + id + "' created", HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	// 2. get All Records
	// http://localhost:9090/rest/employee/all
	// @GetMapping("/all")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		ResponseEntity<?> resp = null;
		List<Employee> list = service.getAllEmployees();
		if (list == null || list.isEmpty()) {
			String message = "No Data Found";
			resp = new ResponseEntity<String>(message, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
		}
		return resp;
	}

	// 3. delete based on id , if exist
	// @DeleteMapping("/delete/{id}")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteById(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		// check for exist
		boolean present = service.isPresent(id);
		if (present) {
			// if exist
			service.deleteEmployee(id);
			resp = new ResponseEntity<String>("Deleted '" + id + "' successfully", HttpStatus.OK);
		} else { // not exist
			resp = new ResponseEntity<String>("'" + id + "' Not Exist", HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	// 4. update data
	// @PutMapping("/update")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@RequestBody Employee employee) {
		ResponseEntity<String> resp = null;
		// check for id exist
		boolean present = service.isPresent(employee.getEmpId());
		if (present) { // if exist
			service.updateEmployee(employee);
			resp = new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
		} else {

			// not exist
			resp = new ResponseEntity<String>("Record '" + employee.getEmpId() + " ' not found",
					HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	// 5. get Records based on id
	//@GetMapping("/getOneEmployee/{id}")
	@RequestMapping(value = "/getOneEmployee/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
		ResponseEntity<?> resp = null;
		Optional<Employee> empData = service.getOneEmployee(id);
		if (empData == null) {
			String message = "No Data Found";
			resp = new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		} else {
			resp = new ResponseEntity<Optional<Employee>>(empData,HttpStatus.OK);
		}
		return resp;
	}
}
