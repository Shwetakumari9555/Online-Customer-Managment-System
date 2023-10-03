package com.servicehub.service;

import java.util.List;
import java.util.Optional;

import com.servicehub.exception.CustomerException;
import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DuplicateValueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperationFailedException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Login;

public interface CustomerService {
   public Customer registerCustomer(Customer customer) throws DuplicateValueException ;
   
   public Customer findByUsername(String username) throws NotFoundException ;
   
// String login(Login login);
   
   Issue viewIssueById(Integer issueId) throws DatabaseOperationException, NotFoundException;

   Issue reopenIssue(Integer issueId) throws  CustomerException, NotFoundException;

//   List<Issue> viewAllIssues() throws DatabaseOperationException;

   String changePassword(Login login) throws NotFoundException;

   String forgotPassword(Integer customerId,String newPassword) throws NotFoundException;
   public List<Issue> allIssues(Integer customerId) throws CustomerException;
   String emailPassword(Integer customerId) throws NotFoundException;


}