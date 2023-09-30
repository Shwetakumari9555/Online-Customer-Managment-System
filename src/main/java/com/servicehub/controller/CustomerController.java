package com.servicehub.controller;


import com.servicehub.model.Login;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.servicehub.exception.CustomerException;
import com.servicehub.exception.IssueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.service.CustomerServiceImpl;
import com.servicehub.service.CustomerService;

import jakarta.validation.Valid;

@RestController
//@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerServiceImpl customerService;
   

	@Autowired
	private PasswordEncoder  passwordEncoder;


	@GetMapping("/signIn")
	public ResponseEntity<String> logInUserHandler(Authentication auth) throws CustomerException, NotFoundException {
	    String username = auth.getUsername(); // Get the username from authentication
	    String password = auth.getPassword(); // Get the password from authentication

	    Customer customer = customerService.findByUsername(username);

	    if (customer != null) {
	        // Verify the provided password against the stored password
	        if (passwordEncoder.matches(password, customer.getLogin().getPassword())) {
	            // Password matches, user is authenticated
	            return ResponseEntity.ok(customer.getFirstName() + " Logged In Successfully");
	        } else {
	            // Password does not match, handle authentication failure
	            throw new CustomerException("Authentication failed: Incorrect password");
	        }
	    } else {
	        // If the customer is not found, you can handle this as needed
	        // For example, you can throw a CustomerException or return a different response
	        throw new CustomerException("Customer not found");
	    }
	
}

	 @PostMapping("/customers")
	 public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customerRequest) throws CustomerException, NotFoundException {
	     // Create a new Customer entity
	     Customer customer = new Customer();
	     customer.setFirstName(customerRequest.getFirstName());
	     customer.setLastName(customerRequest.getLastName());
	     customer.setEmail(customerRequest.getEmail());
	     customer.setMobile(customerRequest.getMobile());
	     customer.setCity(customerRequest.getCity());

	     // Create a new Login entity
	     Login login = new Login();
	     login.setUsername(customerRequest.getLogin().getUsername());

	     // Set the generated password in the Login entity
	     login.setPassword(passwordEncoder.encode(customerRequest.getLogin().getPassword()));
	     login.setType(customerRequest.getLogin().getType());
	     login.setActive(customerRequest.getLogin().isActive());

	     // Set the Login entity in the Customer
	     customer.setLogin(login);

	     Customer cust = customerService.registerCustomer(customer);

	     return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);
	 }

	 

	 
	 
//	@PostMapping("/raiseIssue/{customerId}")
//	public ResponseEntity<Issue> registerIssue(@Valid @RequestBody Issue issue, @PathVariable int customerId ) throws IssueException, CustomerException {
//		
//		Issue is = customerService.registerIssue(customerId, issue);
//
//		return new ResponseEntity<Issue>(is, HttpStatus.CREATED);
//
//	}
//
//	@GetMapping("/findIssueById/{issueId}/{cid}")
//	public ResponseEntity<Issue> findIssueById(@PathVariable int issueId, @PathVariable int cid)
//			throws IssueException, CustomerException {
//
//		Issue issue = customerService.viewlssueByld(cid, issueId);
//
//		return new ResponseEntity<Issue>(issue, HttpStatus.OK);
//
//	}
//
//	@PutMapping("/changePassword/{customerId}") // no need to pass request body
//	public ResponseEntity<String> changePassword(@PathVariable int customerId, @RequestParam String newPassword,
//			@RequestParam String oldPassword) throws OperatorException, CustomerException, LoginException {
//		String str = customerService.changePassword(customerId, newPassword, oldPassword);
//		return new ResponseEntity<String>(str, HttpStatus.OK);
//	}
//
//	@PutMapping("/customer/forgetPassword") // no need to pass request body
//	public ResponseEntity<Integer> forgetPassword(@Valid @RequestBody LoginDTO ld, @RequestParam String mobile)
//			throws OperatorException, CustomerException, LoginException {
//		Integer str = customerService.forgotPassword(ld, mobile);
//		return new ResponseEntity<Integer>(str, HttpStatus.OK);
//	}
//
//	@GetMapping("/viewAllIssue/{customerId}")
//	public ResponseEntity<List<Issue>> allissue(@PathVariable int customerId) throws CustomerException, IssueException {
//		List<Issue> cus = customerService.viewAllIssues(customerId);
//
//		return new ResponseEntity<List<Issue>>(cus, HttpStatus.ACCEPTED);
//	}
}