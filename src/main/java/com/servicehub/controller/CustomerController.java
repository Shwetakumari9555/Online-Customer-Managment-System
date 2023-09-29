package com.servicehub.controller;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicehub.exception.CustomerException;
import com.servicehub.exception.IssueException;
import com.servicehub.exception.OperatorException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.repository.CustomerRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
   @Autowired
   private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder  passwordEncoder;


	 @GetMapping("/signin")
		public ResponseEntity<String> logInUserHandler(Authentication auth) throws CustomerException{
			 Customer opt= customerService.findByUsername(auth.getName());
			 return new ResponseEntity<>(opt.getUsername()+" Logged In Successfully", HttpStatus.ACCEPTED);
		}

	@PostMapping("/")
	public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) throws CustomerException {
		customer.setPassword(passwordEncoder.encode(customer.getPassword())) ;
		Customer cust = customerService.registerCustomer(customer);

		return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);

	}

	@PostMapping("/raiseIssue/{customerId}")
	public ResponseEntity<Issue> registerIssue(@Valid @RequestBody Issue issue, @PathVariable int customerId ) throws IssueException, CustomerException {
		
		Issue is = customerService.registerIssue(customerId, issue);

		return new ResponseEntity<Issue>(is, HttpStatus.CREATED);

	}

	@GetMapping("/findIssueById/{issueId}/{cid}")
	public ResponseEntity<Issue> findIssueById(@PathVariable int issueId, @PathVariable int cid)
			throws IssueException, CustomerException {

		Issue issue = customerService.viewlssueByld(cid, issueId);

		return new ResponseEntity<Issue>(issue, HttpStatus.OK);

	}

	@PutMapping("/changePassword/{customerId}") // no need to pass request body
	public ResponseEntity<String> changePassword(@PathVariable int customerId, @RequestParam String newPassword,
			@RequestParam String oldPassword) throws OperatorException, CustomerException, LoginException {
		String str = customerService.changePassword(customerId, newPassword, oldPassword);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@PutMapping("/customer/forgetPassword") // no need to pass request body
	public ResponseEntity<Integer> forgetPassword(@Valid @RequestBody LoginDTO ld, @RequestParam String mobile)
			throws OperatorException, CustomerException, LoginException {
		Integer str = customerService.forgotPassword(ld, mobile);
		return new ResponseEntity<Integer>(str, HttpStatus.OK);
	}

	@GetMapping("/viewAllIssue/{customerId}")
	public ResponseEntity<List<Issue>> allissue(@PathVariable int customerId) throws CustomerException, IssueException {
		List<Issue> cus = customerService.viewAllIssues(customerId);

		return new ResponseEntity<List<Issue>>(cus, HttpStatus.ACCEPTED);
	}
}