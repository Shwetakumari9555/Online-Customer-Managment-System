//package com.servicehub.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.servicehub.exception.CustomerException;
//import com.servicehub.exception.OperatorException;
//import com.servicehub.model.Customer;
//import com.servicehub.model.Issue;
//import com.servicehub.model.Operator;
//import com.servicehub.service.OperatorServiceImpl;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/operator")
//public class OperatorController {
//   
//	@Autowired
//	OperatorService operatorService; 
//	@Autowired
//	IssueService issueService;
//
//	 @GetMapping("/signin")
//		public ResponseEntity<String> logInUserHandler(Authentication auth) throws OperatorException{
//			 Operator opt= operatorService.findByUsername(auth.getName());
//			 return new ResponseEntity<>(opt.getUsername()+" Logged In Successfully", HttpStatus.ACCEPTED);
//		}
//
//	@GetMapping("/findCustomerById/{customerId}")
//	public ResponseEntity<Customer> findCustomerById(@PathVariable Integer customerId) throws OperatorException {
//	    Customer foundCustomer = operatorService.findCustomerById(customerId);
//	    return new ResponseEntity<Customer>(foundCustomer, HttpStatus.OK);
//	}
//	
//	
//	 @GetMapping("/findCustomerByfirstName")
//	    public ResponseEntity<List<Customer>> findCustomerByName(@RequestParam String name) throws OperatorException {
//	        List<Customer> foundCustomers = operatorService.findCustomerByfirstName(name);
//	        return new ResponseEntity<List<Customer>>(foundCustomers, HttpStatus.OK);
//	    }
//	 @GetMapping("/findByUsername/Email")
//	    public ResponseEntity<Customer> findByUsername(@RequestParam String Email) throws OperatorException {
//	        Customer foundCustomers = operatorService.findCustomerByUserName(Email);
//	        return new ResponseEntity<Customer>(foundCustomers, HttpStatus.OK);
//	    }
//	 
//	 @PutMapping("/modifyIssue/{issueId}/{customerId}/{status}")
//	 public ResponseEntity<Issue> modifyIssue(@Valid @RequestBody Issue issue, @PathVariable Integer issueId,
//	                                          @PathVariable IssueStatus status, @PathVariable int customerId) throws OperatorException, CustomerException {
//	     Issue updatedIssue = issueService.modifyCustomerIssue(issue, issueId, status, customerId);
//	     return new ResponseEntity<Issue>(updatedIssue, HttpStatus.OK);
//	 }
//	    
//	    @PutMapping("/closeIssue/{issueId}") // no need to pass request body
//	    public ResponseEntity<Issue> closeIssue(@PathVariable Integer issueId) throws OperatorException {
//	        Issue closedIssue = issueService.closeCustomerIssue(issueId);
//	        return new ResponseEntity<Issue>(closedIssue, HttpStatus.OK);
//	    }
//}
