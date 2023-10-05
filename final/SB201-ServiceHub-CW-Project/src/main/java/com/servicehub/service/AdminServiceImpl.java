package com.servicehub.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DepartmentAlreadyExistsException;
import com.servicehub.exception.DepartmentNotFoundException;
import com.servicehub.exception.DuplicateValueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorAlreadyExistsException;
import com.servicehub.model.Department;
import com.servicehub.model.Login.UserType;
import com.servicehub.model.Operator;
import com.servicehub.model.Login;
import com.servicehub.repository.DepartmentRepository;
import com.servicehub.repository.LoginRepository;
import com.servicehub.repository.OperatorRepository;

@Service
public class AdminServiceImpl implements AdminService {

	  @Autowired
	  private DepartmentRepository departmentRepository; 
	  
	  @Autowired
	  private OperatorRepository operatorRepository;
	  
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
		 @Autowired
		 private LoginRepository loginRepository;

	    @Override
	    public boolean addDepartment(Department department) throws DatabaseOperationException, DepartmentAlreadyExistsException {
	        try {
	            // Check if the department with the same name already exists
	            if (departmentRepository.existsByDepartmentName(department.getDepartmentName())) {
	                throw new DepartmentAlreadyExistsException("Department with the same name already exists.");
	            }

	            // If not, save the department
	            departmentRepository.save(department);
	            return true;
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to add department to the database.");
	        }
	    }

	    @Override
	    public String removeDepartment(Integer departmentId) throws  DepartmentNotFoundException {
	        Optional<Department> existingDepartment = departmentRepository.findById(departmentId);

	        if (!existingDepartment.isPresent()) {
	            throw new DepartmentNotFoundException("Department doesn't exist with given departmentId");
	        }

	        departmentRepository.deleteById(departmentId);

	        return "Department with Id: "+departmentId+ " Deleted Successfully";
	    }

	           

	    @Override
		public Department modifyDepartment(Department department, Integer departmentId) throws  DepartmentNotFoundException {

		    Optional<Department> existingDepartment = departmentRepository.findById(departmentId);

		    if (!existingDepartment.isPresent()) {
		        throw new DepartmentNotFoundException("Department doesn't exist with given departmentId");
		    }

		    Department departmentToUpdate = existingDepartment.get();
		    departmentToUpdate.setDepartmentName(department.getDepartmentName());

		    Department updatedDepartment = departmentRepository.save(departmentToUpdate);

		    return updatedDepartment;
		}

	    @Override
	    public Department findDepartmentById(Integer departmentId) throws DepartmentNotFoundException {

			Optional<Department> existingDepartment = departmentRepository.findById(departmentId);
			
			if(!existingDepartment.isPresent()) 
				throw new DepartmentNotFoundException("Department doesn't exist with given departmentId");
			
			return existingDepartment.get();
	    }
	    
	    
        //fixed--
	    @Override
	    public Operator addOperator(Operator operator) throws DuplicateValueException {
	        // Create a new Login entity for the operator
	    	
	    	 Optional<Login> findUsername = loginRepository.findByUsername(operator.getLogin().getUsername());
	         
	         if (findUsername.isPresent() && findUsername.get().getUsername().equals(operator.getLogin().getUsername())) {
	             throw new DuplicateValueException("Username already exists");
	         }
	    	
	        Login login = new Login();
	        login.setUsername(operator.getLogin().getUsername());
	        login.setPassword(passwordEncoder.encode(operator.getLogin().getPassword())); // Encode the password
	        login.setType(operator.getLogin().getType());
	        
	        // Set the Login entity in the Operator
	        loginRepository.save(login);
	        
	        operator.setLogin(login);
	        
	        // Save both Operator and Login entities
	        operatorRepository.save(operator);

	        return operator;
	    }


	    @Override
	    public String removeOperator(Integer operatorId) throws DatabaseOperationException {
	        Optional<Operator> existingOperator = operatorRepository.findById(operatorId);

	        if (!existingOperator.isPresent()) {
	            throw new DatabaseOperationException("Operator doesn't exist with given operatorId");
	        }

	        Operator deletedOperator = existingOperator.get();
	        operatorRepository.deleteById(operatorId);

	        return "Operator with id : "+operatorId+" deleted successfully"; 
	    }

		
		

		@Override
	    public Operator modifyOperator(Operator operator, Integer operatorId) throws DatabaseOperationException {
			
	        Operator existingOperator = operatorRepository.findById(operatorId).orElseThrow(()-> new DatabaseOperationException("Not Found"));

	       existingOperator.setFirstName(operator.getFirstName());
	       existingOperator.setLastName(operator.getLastName());
	        existingOperator.setEmail(operator.getEmail());
	        existingOperator.setMobile(operator.getMobile());
	        existingOperator.setCity(operator.getCity());
	        existingOperator.setCalls(operator.getCalls());
	        existingOperator.setDepartment(operator.getDepartment());
	        
	        return operatorRepository.save(existingOperator);
		}

		

	    @Override
	    public Operator findOperatorById(Integer operatorId) throws NotFoundException {
	    	

			Optional<Operator> existingOperator = operatorRepository.findById(operatorId);
			
			if(!existingOperator.isPresent()) 
				throw new NotFoundException("Operator doesn't exist with with given operatorId");
			
			return existingOperator.get();
	    }


	    @Override
		public List<Operator> findAllOperators() throws NotFoundException {

			List<Operator> operators = operatorRepository.findAll();
			
			if (operators.isEmpty()) {
				throw new NotFoundException("No operators found");
			}
			
			return operators;
		}



	



}
