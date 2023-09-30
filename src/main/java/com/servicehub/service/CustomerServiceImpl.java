package com.servicehub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.servicehub.exception.NotFoundException;
import com.servicehub.model.*;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.repository.LoginRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	    private final CustomerRepository customerRepository;
	    private final LoginRepository loginRepository;
	    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, LoginRepository loginRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.loginRepository = loginRepository;
		this.passwordEncoder = passwordEncoder;
	}



    @Override
    public Customer registerCustomer(Customer customer) {
    	// Create a new Login entity
        Login login = new Login();
        
//        loginRepository.findByUsername(customer.getLogin().getUsername());
        	
        
        
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



	@Override
	public String login(Login login) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Issue viewIssueById(int issueId) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Issue reopenIssue(int issueId) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Issue> viewAllIssues() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String changePassword(Login login) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String forgotPassword(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Customer emailPassword(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}





}
