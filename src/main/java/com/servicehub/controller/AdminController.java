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

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DepartmentAlreadyExistsException;
import com.servicehub.exception.DepartmentException;
import com.servicehub.exception.DepartmentNotFoundException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorAlreadyExistsException;
import com.servicehub.exception.OperatorException;
import com.servicehub.model.Department;
import com.servicehub.model.Operator;
import com.servicehub.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	 @Autowired
	 private AdminService adminService; 

	 @PostMapping("/departments/add")
	    public ResponseEntity<String> addDepartment(@RequestBody Department department) {
	        try {
	            // Call the service layer to add the department
	            boolean added = adminService.addDepartment(department);
	            
	            if (added) {
	                return ResponseEntity.status(HttpStatus.CREATED).body("Department added successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.CONFLICT).body("Department with the same name already exists");
	            }
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add department to the database");
	        } catch (DepartmentAlreadyExistsException e) {
	            // Handle the case where the department already exists
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Department with the same name already exists");
	        }
	    }
	 
	 @DeleteMapping("/departments/remove/{departmentId}")
	    public ResponseEntity<String> removeDepartment(@PathVariable int departmentId) {
	        try {
	            // Call the service layer to remove the department
	            boolean removed = adminService.removeDepartment(departmentId);
	            
	            if (removed) {
	                return ResponseEntity.ok("Department removed successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with the given ID does not exist");
	            }
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove department from the database");
	        }
	    }
	 
	    @PutMapping("/departments/modify")
	    public ResponseEntity<Department> modifyDepartment(@RequestBody Department department) {
	        try {
	            // Call the service layer to modify the department
	            Department updatedDepartment = adminService.modifyDepartment(department);
	            
	            if (updatedDepartment != null) {
	                return ResponseEntity.ok(updatedDepartment);
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	            }
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        } catch (DepartmentNotFoundException e) {
	            // Handle the case where the department with the given ID does not exist
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	    
	    @GetMapping("/departments/{departmentId}")
	    public ResponseEntity<Department> findDepartmentById(@PathVariable int departmentId) {
	        try {
	            // Call the service layer to find the department by ID
	            Department department = adminService.findDepartmentById(departmentId);
	            
	            return ResponseEntity.ok(department);
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        } catch (DepartmentNotFoundException e) {
	            // Handle the case where the department with the given ID does not exist
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	    
	    @PostMapping("/operators/add")
	    public ResponseEntity<String> addOperator(@RequestBody Operator operator) {
	        try {
	            // Call the service layer to add the operator
	            boolean added = adminService.addOperator(operator);

	            if (added) {
	                return ResponseEntity.status(HttpStatus.CREATED).body("Operator added successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.CONFLICT).body("Operator with the same username already exists");
	            }
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add operator to the database");
	        }  catch (OperatorAlreadyExistsException e) {
	            // Handle the case where an operator with the same username already exists
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Operator with the same username already exists");
	        }
	    }
	    
	    @DeleteMapping("/operators/remove/{operatorId}")
	    public ResponseEntity<String> removeOperator(@PathVariable int operatorId) {
	        try {
	            // Call the service layer to remove the operator
	            boolean removed = adminService.removeOperator(operatorId);
	            
	            if (removed) {
	                return ResponseEntity.ok("Operator removed successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator with the given ID does not exist");
	            }
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove operator from the database");
	        }
	    }
	    
	    
	    @PutMapping("/operators/modify")
	    public ResponseEntity<Operator> modifyOperator(@RequestBody Operator operator) {
	        try {
	            // Call the service layer to modify the operator
	            Operator updatedOperator = adminService.modifyOperator(operator);
	            
	            if (updatedOperator != null) {
	                return ResponseEntity.ok(updatedOperator);
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	            }
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        } catch (NotFoundException e) {
	            // Handle the case where the operator with the given ID does not exist
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }

    
	    @GetMapping("/operators/{operatorId}")
	    public ResponseEntity<Operator> findOperatorById(@PathVariable int operatorId) {
	        try {
	            // Call the service layer to find the operator by ID
	            Operator operator = adminService.findOperatorById(operatorId);
	            
	            return ResponseEntity.ok(operator);
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        } catch (NotFoundException e) {
	            // Handle the case where the operator with the given ID does not exist
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	    
	    @GetMapping("/operators/all")
	    public ResponseEntity<List<Operator>> findAllOperators() {
	        try {
	            // Call the service layer to retrieve all operators
	            List<Operator> operators = adminService.findAllOperators();
	            
	            return ResponseEntity.ok(operators);
	        } catch (DatabaseOperationException e) {
	            // Handle database-related exceptions
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
}