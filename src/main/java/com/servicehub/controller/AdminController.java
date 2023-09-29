package com.servicehub.controller;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicehub.exception.DepartmentException;
import com.servicehub.exception.OperatorException;
import com.servicehub.model.Department;
import com.servicehub.model.Operator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
    
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private PasswordEncoder  passwordEncoder;

	 @GetMapping("/signin")
		public ResponseEntity<String> logInUserHandler(Authentication auth) throws  LoginException{
			 Admin opt= adminService.findByUsername(auth.getName());
			 return new ResponseEntity<>(opt.getUsername()+" Logged In Successfully", HttpStatus.ACCEPTED);
		}

	
	@PostMapping("/")
	public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws LoginException {
		admin.setPassword(passwordEncoder.encode(admin.getPassword())) ;
		Admin createdAdmin = adminService.createAdmin(admin);
		return new ResponseEntity<Admin>(createdAdmin, HttpStatus.CREATED);
	}
   
	@PostMapping("/addOperator")
	public ResponseEntity<Operator> createOperator(@RequestBody Operator operator) throws LoginException, OperatorException {
		operator.setPassword(passwordEncoder.encode(operator.getPassword())) ;
		Operator createdOperator = operatorService.createOperator(operator);
		return new ResponseEntity<Operator>(createdOperator, HttpStatus.CREATED);
	}
	
	

	@PostMapping("/addDepartments")
	public ResponseEntity<Department> addDepartment(@RequestBody Department department) throws LoginException {
		Department savedDepartment = adminService.addDepartment(department);
		return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
	}

	@DeleteMapping("/departments/{departmentId}")
	public ResponseEntity<Object> removeDepartment(@PathVariable int departmentId)
			throws DepartmentException, LoginException {
		Department removedDepartment = adminService.removeDepartment(departmentId);
		return new ResponseEntity<>(removedDepartment, HttpStatus.OK);
	}

	@PutMapping("/departments/{departmentId}/{name}")
	public ResponseEntity<Object> modifyDepartment(@PathVariable int departmentId, @PathVariable String name)
			throws DepartmentException, LoginException {
		Department modifiedDepartment = adminService.modifyDepartment(departmentId, name);
		return new ResponseEntity<>(modifiedDepartment, HttpStatus.OK);
	}

	@GetMapping("/departments/{departmentId}")
	public ResponseEntity<Object> findDepartmentById(@PathVariable int departmentId)
			throws DepartmentException, LoginException {
		Department department = adminService.findDepartmentById(departmentId);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@PostMapping("/operators/{departmentId}")
	public ResponseEntity<Operator> addOperator(@Valid @RequestBody Operator operator, @PathVariable int departmentId)
			throws LoginException, DepartmentException {
		Operator savedOperator = adminService.addOperator(operator, departmentId);
		return new ResponseEntity<>(savedOperator, HttpStatus.CREATED);
	}

	@DeleteMapping("/operators/{operatorId}")
	public ResponseEntity<Object> removeOperator(@PathVariable int operatorId)
			throws OperatorException, LoginException {
		Operator removedOperator = adminService.removeOperatorById(operatorId);
		return new ResponseEntity<>(removedOperator, HttpStatus.OK);
	}

	@PutMapping("/operators/{operatorId}")
	public ResponseEntity<Object> modifyOperator(@PathVariable int operatorId, @RequestBody Operator operator)
			throws OperatorException, LoginException {
		Operator modifiedOperator = adminService.modifyOperator(operatorId, operator);
		return new ResponseEntity<>(modifiedOperator, HttpStatus.OK);
	}

	@GetMapping("/operators/{operatorId}")
	public ResponseEntity<Object> findOperatorById(@PathVariable int operatorId)
			throws OperatorException, LoginException {
		Operator operator = adminService.findOperatorById(operatorId);
		return new ResponseEntity<>(operator, HttpStatus.OK);
	}

	@GetMapping("/allOperators")
	public ResponseEntity<List<Operator>> getAllOperators() throws LoginException {
		List<Operator> allOperators = adminService.getAllOperators();
		return new ResponseEntity<>(allOperators, HttpStatus.OK);
	}
}