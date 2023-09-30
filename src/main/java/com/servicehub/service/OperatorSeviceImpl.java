package com.servicehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.model.Login;

@Service
public class OperatorSeviceImpl implements OperatorService {

	@Override
	public String login(Login login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Issue addCustomerIssue(Issue issue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendIntimationEmailToCustomer(int customerId, int issueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Issue modifyCustomerIssue(Issue issue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendModificationEmailToCustomer(int customerId, int issueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Issue closeCustomerIssue(Issue issue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findCustomerById(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findCustomerByName(String customerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findCustomerByEmail(String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean lockCustomer(int customerId) {
		// TODO Auto-generated method stub
		return false;
	}

}
