package com.servicehub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperationFailedException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Issue_Status;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.repository.IssueRepository;
import com.servicehub.repository.OperatorRepository;

@Service
public class OperatorSeviceImpl implements OperatorService {

	
	@Autowired
    private IssueRepository issueRepository; 
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private OperatorRepository operatorRepository;
	


    @Override
    public Issue addCustomerIssue(Issue issue) throws DatabaseOperationException {
    	if (issue == null) {
			throw new DatabaseOperationException("Issue can't be null");
		}

		return issueRepository.save(issue);
    }

    @Override
    public String sendIntimationEmailToCustomer(Integer customerId, Integer issueId) throws NotFoundException, OperationFailedException {
        try {
            // Load the customer and issue objects from the database
            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            Optional<Issue> optionalIssue = issueRepository.findById(issueId);

            if (!optionalCustomer.isPresent() || !optionalIssue.isPresent()) {
                throw new NotFoundException("Customer or issue not found.");
            }

            Customer customer = optionalCustomer.get();
            Issue issue = optionalIssue.get();


            // Return a success message
            return "Intimation email sent successfully to customer: " + customer.getFirstName();
        } catch (Exception ex) {
            // Handle any other exceptions, e.g., email sending failures
            throw new OperationFailedException("Failed to send intimation email.");
        }
    }

    @Override
    public Issue modifyCustomerIssue(Integer issueId, Issue issue) throws DatabaseOperationException, NotFoundException {
    	if (issue == null) {
			throw new NotFoundException("Issue can't be null");
		}
		
		Optional<Issue> existingIssue = issueRepository.findById(issueId);
		
		if(!existingIssue.isPresent()) 
			throw new NotFoundException("Issue doesn't exist with given issueId");
		
		
		Issue issueToUpdate = existingIssue.get();
	    issueToUpdate.setIssueType(issue.getIssueType());
	    issueToUpdate.setDescription(issue.getDescription());
	    issueToUpdate.setStatus(issue.getStatus());

	    Issue updatedIssue = issueRepository.save(issueToUpdate);
		
		return updatedIssue;
    }

    @Override
    public String sendModificationEmailToCustomer(Integer customerId, Integer issueId) throws OperationFailedException, NotFoundException {
        try {
            // Load the customer and issue objects from the database
            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            Optional<Issue> optionalIssue = issueRepository.findById(issueId);

            if (!optionalCustomer.isPresent() || !optionalIssue.isPresent()) {
                throw new NotFoundException("Customer or issue not found.");
            }

            Customer customer = optionalCustomer.get();
            Issue issue = optionalIssue.get();

            // Return a success message
            return "Modification email sent successfully to customer: " + customer.getFirstName() + issue;
        } catch (Exception ex) {
            // Handle any other exceptions, e.g., email sending failures
            throw new OperationFailedException("Failed to send modification email.");
        }
    }



	 @Override
	    public Issue closeCustomerIssue(Issue issue ,Integer issueId) throws DatabaseOperationException, NotFoundException{
			Issue existingIssue = issueRepository.findById(issueId)
	                .orElseThrow(() -> new NotFoundException("Issue not found with id: " + issueId));

	        if (existingIssue.getStatus() == Issue_Status.PENDING) {
	            existingIssue.setStatus(Issue_Status.RESOLVED);
	            issueRepository.save(existingIssue);
	        } else {
	            throw new NotFoundException("Issue is not in PENDING status");
	        }
			return existingIssue;
	    }

	    @Override
	    public List<Customer> findCustomerByName(String customerName) throws DatabaseOperationException {
	    	if (customerName == null || customerName.isEmpty()) {
	            throw new DatabaseOperationException("Name cannot be empty");
	        }
	    	List<Customer> customers = customerRepository.findCustomerByFirstName(customerName);
			return customers;
	    }

	    @Override
	    public Customer findCustomerByEmail(String customerEmail)throws DatabaseOperationException, NotFoundException {
	    	if (customerEmail == null) {
				throw new NotFoundException("CustomerId can't be null");
			}

			Optional<Customer> existingCustomer = customerRepository.findByEmail(customerEmail);
			
			if(existingCustomer.isEmpty()) 
				throw new NotFoundException("No customer exists with given email");
			
			return existingCustomer.get();
	    }





	 @Override
	    public Customer findCustomerById(Integer customerId) throws DatabaseOperationException, NotFoundException {
		 if(customerId==null) {
			 throw new NotFoundException("customerId can't be null");
		 }

			Optional<Customer> existingCustomer = customerRepository.findById(customerId);
			
			if(existingCustomer.isEmpty()) 
				throw new NotFoundException("No customer exists with given customerId");
			
			return existingCustomer.get();
	    }
	 
		@Override
	    public boolean lockCustomer(Integer customerId) throws DatabaseOperationException, NotFoundException {
	        try {
	            // Use the CustomerRepository to find the customer by ID
	            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

	            if (!optionalCustomer.isPresent()) {
	                throw new NotFoundException("Customer with the given ID does not exist.");
	            }

	            Customer customer = optionalCustomer.get();

	            customerRepository.save(customer);

	            return true; 
	        } catch (DataAccessException ex) {
	           
	            throw new DatabaseOperationException("Failed to lock the customer in the database.");
	        }
	    }

}
