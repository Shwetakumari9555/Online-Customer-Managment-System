package com.servicehub.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.servicehub.exception.CustomerException;
import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DuplicateValueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperationFailedException;
import com.servicehub.model.*;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.repository.IssueRepository;
import com.servicehub.repository.LoginRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{
	
	private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

	    private final CustomerRepository customerRepository;
	    private final LoginRepository loginRepository;
	    private final IssueRepository issueRepository;
	    private final PasswordEncoder passwordEncoder;
    
    @Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, LoginRepository loginRepository,
			IssueRepository issueRepository, PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.loginRepository = loginRepository;
		this.issueRepository = issueRepository;
		this.passwordEncoder = passwordEncoder;
	}	



    @Override
    public Customer registerCustomer(Customer customer) throws DuplicateValueException {
    	// Create a new Login entity
        Login login = new Login();
        
        Optional<Login> findUsername = loginRepository.findByUsername(customer.getLogin().getUsername());
        
        if (findUsername.isPresent() && findUsername.get().getUsername().equals(customer.getLogin().getUsername())) {
            throw new DuplicateValueException("Username already exists");
        }
        	
        
        
        login.setUsername(customer.getLogin().getUsername()); // Set the username from Customer
        login.setPassword(passwordEncoder.encode(customer.getLogin().getPassword())); // Set the password from Customer
        login.setType(customer.getLogin().getType());

        // Set the Login entity in the Customer
        customer.setLogin(login);

        // Save both Customer and Login entities
        loginRepository.save(login);
     
        return customerRepository.save(customer);
    }





	@Override
	public Customer findByUsername(String username) throws NotFoundException {
    Optional<Customer> findCustomer = customerRepository.findCustomerByUsername(username);
    
		
		if(!findCustomer.isPresent()) {
			throw new NotFoundException("Customer is not Present with this Username :" + username);
		}
		
		return  findCustomer.get();
	}
//    @Override
//    public Customer findByUsername(String username)throws NotFoundException {
//        logger.info("Searching for customer by username: {}", username);
//
//        Optional<Customer> customer = customerRepository.findCustomerByUsername(username);
//
//        if (customer.isPresent()) {
//            logger.info("Customer found: {}", customer.get());
//        } else {
//            logger.info("Customer not found for username: {}", username);
//        }
//
//        return customer.get();
//    }



	 @Override
	    public Issue viewIssueById(Integer issueId) throws DatabaseOperationException,NotFoundException {
		 if(issueId==null) {
			 throw new NotFoundException("issueId should not be null");
		 }

			Optional<Issue> issue = issueRepository.findById(issueId);
			
			if(issue.isEmpty())
				throw new NotFoundException("Issue doesn't exist with given issueId for the given Customer");
			
			return issue.get();
			
	 }

	    @Override
	    
	    public Issue reopenIssue(Integer issueId) throws  CustomerException, NotFoundException {
	    	
	    	if(issueId==null) {
				 throw new NotFoundException("issueId should not be null");
			 }
	    	 Issue issue = issueRepository.findById(issueId).orElseThrow(()-> new CustomerException("Issue not find"));
//	 	            .orElseThrow(() -> new CustomerException("Issue not found with ID: " + issueId));

	 	    if (issue.getStatus() == Issue_Status.RESOLVED) {
	 	        issue.setStatus(Issue_Status.PENDING);
	 	       
	 	        return issueRepository.save(issue);
	 	    } else {
	 	        throw new CustomerException("Cannot reopen issue. The issue is not in the RESOLVED state.");
	 	    }
	    }



//	    @Override
//	    public List<Issue> viewAllIssues() throws DatabaseOperationException {
//	        try {
//	            // Use the IssueRepository to retrieve all issues
//	            List<Issue> allIssues = issueRepository.findAll();
//	            return allIssues;
//	        } catch (DataAccessException ex) {
//	            // Handle database-related exceptions
//	            throw new DatabaseOperationException("Failed to retrieve all issues from the database.");
//	        }
//	    }



	    @Override
	    public String changePassword(Login login) throws NotFoundException {
	        
	        Optional<Login> existingLogin = loginRepository.findByUsername(login.getUsername());

	        if (existingLogin.isPresent()) {
	            
	            Login updatedLogin = existingLogin.get();
	            updatedLogin.setPassword(passwordEncoder.encode(login.getPassword())); 

	            loginRepository.save(updatedLogin);

	            return "Password changed successfully";
	        } else {
	            throw new NotFoundException("Login with username " + login.getUsername() + " not found");
	        }
	    }



	    @Override
	    public String forgotPassword(Integer customerId, String newPassword) throws NotFoundException {
	       
	        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

	        if (optionalCustomer.isPresent()) {
	            Customer customer = optionalCustomer.get();
	           
	            customer.getLogin().setPassword(passwordEncoder.encode(newPassword));
	            customerRepository.save(customer);

	            return "Password reset successful.";
	        } else {
	            throw new NotFoundException("Customer with the given ID does not exist.");
	        }
	    }



	    @Override
	    public String emailPassword(Integer customerId) throws NotFoundException {
	        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

	        if (optionalCustomer.isPresent()) {
	            Customer customer = optionalCustomer.get();

	          String password = customer.getLogin().getPassword();  

	            return "Your password received on email is : "+ password;
	        } else {
	            throw new NotFoundException("Customer with the given ID does not exist.");
	        }

	   }
	    

 

	public List<Issue> allIssues(Integer customerId) throws CustomerException {
		if (customerId == null) {
			throw new CustomerException("CustomerId can't be null");
		}

		 List<Issue> allIssues = issueRepository.findAll();
		return allIssues;
	}








}
