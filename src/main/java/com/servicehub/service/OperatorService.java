package com.servicehub.service;


import java.util.List;

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperationFailedException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Login;

public interface OperatorService {

//    String login(Login login) throws OperationFailedException;

    Issue addCustomerIssue(Issue issue) throws DatabaseOperationException;

    String sendIntimationEmailToCustomer(Integer customerId, Integer issueId) throws NotFoundException, OperationFailedException;

//    Issue modifyCustomerIssue(Issue issue ,Integer issueId) throws DatabaseOperationException, NotFoundException;

    String sendModificationEmailToCustomer(Integer customerId, Integer issueId) throws OperationFailedException, NotFoundException;

//    Issue closeCustomerIssue(Issue issue) throws DatabaseOperationException, NotFoundException;

    Customer findCustomerById(Integer customerId) throws DatabaseOperationException, NotFoundException;

    List<Customer> findCustomerByName(String customerName) throws DatabaseOperationException;

    Customer findCustomerByEmail(String customerEmail) throws DatabaseOperationException, NotFoundException;

    boolean lockCustomer(Integer customerId) throws DatabaseOperationException, NotFoundException;

	Issue modifyCustomerIssue(Integer issueId, Issue issue) throws DatabaseOperationException, NotFoundException;

	Issue closeCustomerIssue(Issue issue, Integer issueId) throws DatabaseOperationException, NotFoundException;

	
}

