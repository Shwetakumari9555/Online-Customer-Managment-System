package com.servicehub.service;


import java.util.List;

import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Login;

public interface OperatorService {

    String login(Login login);

    Issue addCustomerIssue(Issue issue);

    String sendIntimationEmailToCustomer(int customerId, int issueId);

    Issue modifyCustomerIssue(Issue issue);

    String sendModificationEmailToCustomer(int customerId, int issueId);

    Issue closeCustomerIssue(Issue issue);

    Customer findCustomerById(int customerId);

    List<Customer> findCustomerByName(String customerName);

    Customer findCustomerByEmail(String customerEmail);

    boolean lockCustomer(int customerId);
}

