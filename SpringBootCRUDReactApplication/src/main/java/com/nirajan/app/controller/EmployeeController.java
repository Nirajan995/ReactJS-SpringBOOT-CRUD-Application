package com.nirajan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nirajan.app.exception.ResourceNotFoundException;
import com.nirajan.app.model.Employee;
import com.nirajan.app.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository repository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return repository.findAll();
	}
	
	//create employee
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return repository.save(employee);
	}
	
	//get employee by id
	@GetMapping("/employees/{id}")
	public Employee findById(@PathVariable("id") int id ) {
		return repository.findById(id).orElseThrow(()->	new ResourceNotFoundException(id+":id not found")     
		);
	}
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee
				(@PathVariable("id")int id,@RequestBody Employee employeeDetails){
		Employee employee=repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found"));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		Employee updatedEmployee = repository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable("id")int id){
		Employee employee=repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Id not found"));
		repository.delete(employee);
		Map<String,Boolean> resp=new HashMap<>();
		resp.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(resp);
	}
}
