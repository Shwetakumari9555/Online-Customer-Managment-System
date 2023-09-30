package com.servicehub.service;

import java.util.List;

import com.servicehub.exception.NotFoundException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Login;

public interface CustomerService {
   public Customer registerCustomer(Customer customer) throws NotFoundException ;
   
   public Customer findByUsername(String username) throws NotFoundException ;
   String login(Login login);
   
//   String registerCustomer(Customer customer);


   Issue viewIssueById(int issueId);

   Issue reopenIssue(int issueId);

   List<Issue> viewAllIssues();

   String changePassword(Login login);

   String forgotPassword(int customerId);

   Customer emailPassword(int customerId);

}