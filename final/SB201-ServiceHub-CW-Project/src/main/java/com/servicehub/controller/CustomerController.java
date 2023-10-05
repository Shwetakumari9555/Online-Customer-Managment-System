package com.servicehub.controller;



import com.servicehub.exception.CustomerException;
import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DuplicateValueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Issue_Status;
import com.servicehub.model.Login;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
	
	@Autowired
	private CustomerServiceImpl customerService;
   

	@Autowired
	private PasswordEncoder  passwordEncoder;

	
	@GetMapping("/logIn")
	public ResponseEntity<String> logInUserHandler(Authentication auth) throws CustomerException, NotFoundException {
	    String username = auth.getName(); // Get the username from authentication
	    logger.info("Logged in Customer: {}", username);

	    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

	    return ResponseEntity.ok( authorities +":"+ username + " Logged In Successfully");
	}



	 @PostMapping("/add")
	 public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customerRequest) throws DuplicateValueException {
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
//	     login.setPassword(passwordEncoder.encode(customerRequest.getLogin().getPassword()));
	     login.setPassword(customerRequest.getLogin().getPassword());
	     login.setType(customerRequest.getLogin().getType());
	     login.setActive(customerRequest.getLogin().isActive());

	     // Set the Login entity in the Customer
	     customer.setLogin(login);

	     Customer cust = customerService.registerCustomer(customer);

	     return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);
	 }
	 
	 @GetMapping("/viewIssuesById/{issueId}")
		public ResponseEntity<Issue> viewIssuesByIdHandler(@PathVariable Integer issueId) throws CustomerException, DatabaseOperationException, NotFoundException {
			
			Issue issue = customerService.viewIssueById(issueId);
			
			return new ResponseEntity<>(issue, HttpStatus.OK);
		}
		
		

		@PutMapping("/reopen{issueId}")
	    public ResponseEntity<Issue> reopenIssue(@PathVariable Integer issueId) throws CustomerException, NotFoundException {
	      
	            Issue reopenedIssue = customerService.reopenIssue(issueId);
	            reopenedIssue.setStatus(Issue_Status.PENDING);
	            return ResponseEntity.ok(reopenedIssue);
	      
	    }
		
		

		@GetMapping("/allIssues/{customerId}")
		public ResponseEntity<List<Issue>> viewAllIssuesHandler(@PathVariable Integer customerId) throws CustomerException {
			
			List<Issue> issues = customerService.allIssues(customerId);
			
			return new ResponseEntity<List<Issue>>(issues, HttpStatus.OK);
		}
		
	    @PostMapping("/change-password")
	    public ResponseEntity<String> changePassword(@RequestBody Login login) {
	        try {
	            String message = customerService.changePassword(login);
	            return new ResponseEntity<>(message, HttpStatus.OK);
	        } catch (NotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @PostMapping("/forgot-password")
	    public ResponseEntity<String> forgotPassword(@RequestParam Integer customerId, @RequestParam String newPassword) {
	        try {
	            String result = customerService.forgotPassword(customerId, newPassword);
	            return ResponseEntity.ok(result);
	        } catch (NotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	    
	    @GetMapping("/emailPassword/{customerId}")
	    public ResponseEntity<String> emailPassword(@PathVariable Integer customerId) {
	        try {
	            String newPassword = customerService.emailPassword(customerId);
	            return ResponseEntity.ok(newPassword);
	        } catch (NotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with the given ID does not exist.");
	        }
	    }
		
}