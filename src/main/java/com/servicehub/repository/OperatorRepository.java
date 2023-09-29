package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

	Operator findByUsernameAndPassword(String username, String password);
	Operator findByUsername(String username);	
	
}