package com.servicehub.controller;

import java.util.Collection;
import java.util.List;
import com.servicehub.model.*;

import javax.security.auth.login.LoginException;
import com.servicehub.exception.ErrorDetails;

import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

import com.servicehub.exception.CustomerException;
import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DepartmentAlreadyExistsException;
import com.servicehub.exception.DepartmentException;
import com.servicehub.exception.DepartmentNotFoundException;
import com.servicehub.exception.DuplicateValueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorAlreadyExistsException;
import com.servicehub.exception.OperatorException;
import com.servicehub.model.Department;
import com.servicehub.model.Operator;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.repository.LoginRepository;
import com.servicehub.service.AdminService;

import jakarta.validation.Valid;

@RestController
//@RequestMapping("/admin")
public class AdminController {
	
	private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
	
	 @Autowired
	 private AdminService adminService; 
	 
	 @Autowired
	 private LoginRepository loginRepository;
	 
	 
	 @GetMapping("/logIn")
		public ResponseEntity<String> logInUserHandler(Authentication auth) throws CustomerException, NotFoundException {
		    String username = auth.getName(); // Get the username from authentication
		    logger.info("Logged in Admin: {}", username);
		    
		    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		    return ResponseEntity.ok( authorities +":"+ username + " Logged In Successfully");
		}

	 @PostMapping("/admin/departments/add")
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
	 
	 @DeleteMapping("/deleteDepartment/{departmentId}")
	 public ResponseEntity<String> deleteDepartmentHandler(@PathVariable Integer departmentId) {
	     try {
	         String result = adminService.removeDepartment(departmentId);
	         return new ResponseEntity<>(result, HttpStatus.OK);
	     } catch (DepartmentNotFoundException ex) {
	         
	         return new ResponseEntity<>("Department not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	     }
	 }


	 
	 @PutMapping("/modifyDepartment/{departmentId}")
	 public ResponseEntity<Object> modifyDepartment(@RequestBody Department department, @PathVariable Integer departmentId) {
	     try {
	         Department updatedDepartment = adminService.modifyDepartment(department, departmentId);
	         return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
	     } catch (DepartmentNotFoundException ex) {
	         String errorMessage = "Department not found with ID: " + departmentId;
	         return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	     }
	 }

		
	 @GetMapping("/getDepartment/{departmentId}")
	 public ResponseEntity<Object> findDepartmentById(@PathVariable Integer departmentId) {
	     try {
	         Department department = adminService.findDepartmentById(departmentId);
	         return new ResponseEntity<>(department, HttpStatus.OK);
	     } catch (DepartmentNotFoundException ex) {
	    	 String errorMessage = "Department not found with ID: " + departmentId;
	         return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	     } 
	 }

		
	    
	    @PostMapping("/admin/operators/add")
	    public ResponseEntity<Operator> addOperator(@Valid @RequestBody Operator operatorRequest) throws DuplicateValueException {

	        Operator addedOperator = adminService.addOperator(operatorRequest);

	        return new ResponseEntity<>(addedOperator, HttpStatus.CREATED);
	    }
		
	    
	    @DeleteMapping("/deleteOperator/{operatorId}")
	    public ResponseEntity<String> removeOperatorHandler(@PathVariable Integer operatorId) {
	        try {
	            String deletedOperator = adminService.removeOperator(operatorId);
	            return new ResponseEntity<>(deletedOperator, HttpStatus.OK);
	        } catch (DatabaseOperationException ex) {
	            return new ResponseEntity<>("Operator not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }


	    
	 @PutMapping("/modifyOperator/{operatorId}")
		public ResponseEntity<Operator> modifyOperatorHandler(@RequestBody @Valid Operator operator, @PathVariable Integer operatorId)throws DatabaseOperationException {
			
			Operator opt = adminService.modifyOperator(operator, operatorId);
			
			return new ResponseEntity<Operator>(opt,HttpStatus.OK);
		}

    
	 @GetMapping("/operator/{operatorId}")
	 public ResponseEntity<Object> findOperatorByIdHandler(@PathVariable Integer operatorId) {
	     try {
	         Operator operator = adminService.findOperatorById(operatorId);
	         return new ResponseEntity<>(operator, HttpStatus.OK);
	     } catch (NotFoundException ex) {
	    	 String errorMessage = "Operator not found with ID: " + operatorId;
	         return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	     }
	 }

	    
	    @GetMapping("allOperators")
		public ResponseEntity<List<Operator>> getAllOperatorHandler()throws NotFoundException{
			    
			return new ResponseEntity<List<Operator>>(adminService.findAllOperators(), HttpStatus.OK);
		}
}