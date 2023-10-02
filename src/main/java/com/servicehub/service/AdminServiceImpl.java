package com.servicehub.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DepartmentAlreadyExistsException;
import com.servicehub.exception.DepartmentNotFoundException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorAlreadyExistsException;
import com.servicehub.model.Department;
import com.servicehub.model.Login.UserType;
import com.servicehub.model.Operator;
import com.servicehub.repository.DepartmentRepository;
import com.servicehub.repository.OperatorRepository;

@Service
public class AdminServiceImpl implements AdminService {

	  @Autowired
	  private DepartmentRepository departmentRepository; 
	  
	  @Autowired
	  private OperatorRepository operatorRepository;

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
	    public boolean removeDepartment(int departmentId) throws DatabaseOperationException {
	        try {
	            // Check if the department with the given ID exists
	            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
	            
	            if (!optionalDepartment.isPresent()) {
	                return false; // Department with the given ID does not exist
	            }
	            
	            Department department = optionalDepartment.get();

	            // Perform any additional validations or checks here if needed
	            // For example, you can check if the department can be removed based on certain conditions
	            
	            // Perform the removal
	            departmentRepository.delete(department);
	            
	            return true; // Department removed successfully
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to remove department from the database.");
	        }
	    }

	    @Override
	    public Department modifyDepartment(Department department) throws DepartmentNotFoundException, DatabaseOperationException {
	        try {
	            // Check if the department with the given ID exists
	            Optional<Department> optionalExistingDepartment = departmentRepository.findById(department.getDepartmentId());

	            if (!optionalExistingDepartment.isPresent()) {
	                throw new DepartmentNotFoundException("Department with the given ID does not exist.");
	            }

	            Department existingDepartment = optionalExistingDepartment.get();

	            // Perform any additional validations or checks here if needed
	            // For example, you can check if the department can be modified based on certain conditions

	            // Update the existing department's properties with the new values
	            existingDepartment.setDepartmentName(department.getDepartmentName());

	            // Save the updated department
	            Department updatedDepartment = departmentRepository.save(existingDepartment);

	            return updatedDepartment; // Return the updated department
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to modify department in the database.");
	        }
	    }

	    @Override
	    public Department findDepartmentById(int departmentId) throws DepartmentNotFoundException, DatabaseOperationException {
	        try {
	            // Use the DepartmentRepository to find the department by its ID
	            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

	            if (optionalDepartment.isPresent()) {
	                return optionalDepartment.get(); // Return the found department
	            } else {
	                throw new DepartmentNotFoundException("Department with the given ID does not exist.");
	            }
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to retrieve department from the database.");
	        }
	    }

	    @Override
	    public boolean addOperator(Operator operator)throws OperatorAlreadyExistsException, DatabaseOperationException {
	        try {
	            
	            // Check if the operator with the same username already exists
	            if (operatorRepository.existsByUsername(operator.getLogin().getUsername())) {
	                throw new OperatorAlreadyExistsException("Operator with the same username already exists.");
	            }

	            // Save the operator to the database
	            operatorRepository.save(operator);

	            return true; // Operator added successfully
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to add operator to the database.");
	        }
	    }

	    @Override
	    public boolean removeOperator(int operatorId) throws DatabaseOperationException {
	        try {
	            // Check if the operator with the given ID exists
	            Optional<Operator> optionalOperator = operatorRepository.findById(operatorId);

	            if (!optionalOperator.isPresent()) {
	                return false; // Operator with the given ID does not exist
	            }

	            // Remove the operator from the database
	            operatorRepository.deleteById(operatorId);

	            return true; // Operator removed successfully
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to remove operator from the database.");
	        }
	    }

	    @Override
	    public Operator modifyOperator(Operator operator) throws NotFoundException ,DatabaseOperationException {
	        try {
	            // Check if the operator with the given ID exists
	            Optional<Operator> optionalExistingOperator = operatorRepository.findById(operator.getOperatorld());

	            if (!optionalExistingOperator.isPresent()) {
	                throw new NotFoundException("Operator with the given ID does not exist.");
	            }

	            Operator existingOperator = optionalExistingOperator.get();

	            // Perform any additional validations or checks here if needed
	            // For example, you can check if the operator can be modified based on certain conditions

	            // Update the existing operator's properties with the new values
	            existingOperator.getLogin().setUsername(operator.getLogin().getUsername());
	            existingOperator.getLogin().setPassword(operator.getLogin().getPassword());
	            existingOperator.getLogin().setType(operator.getLogin().getType());

	            // Save the updated operator to the database
	            Operator updatedOperator = operatorRepository.save(existingOperator);

	            return updatedOperator; // Return the updated operator
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to modify operator in the database.");
	        }
	    }

	    @Override
	    public Operator findOperatorById(int operatorId) throws DatabaseOperationException, NotFoundException {
	        try {
	            // Use the OperatorRepository to find the operator by ID
	            Optional<Operator> optionalOperator = operatorRepository.findById(operatorId);

	            if (optionalOperator.isPresent()) {
	                return optionalOperator.get(); // Return the found operator
	            } else {
	                throw new NotFoundException("Operator with the given ID does not exist.");
	            }
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to retrieve operator from the database.");
	        }
	    }


	    @Override
	    public List<Operator> findAllOperators() throws DatabaseOperationException {
	        try {
	            // Use the OperatorRepository to find all operators
	            return operatorRepository.findAll();
	        } catch (DataAccessException ex) {
	            // Handle database-related exceptions
	            throw new DatabaseOperationException("Failed to retrieve operators from the database.");
	        }
	    }


	



}
