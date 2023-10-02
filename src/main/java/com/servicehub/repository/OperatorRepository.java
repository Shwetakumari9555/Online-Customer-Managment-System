package com.servicehub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

	boolean existsByUsername(String username);

	Page<Operator> findAll(Pageable pageable);

//	Operator findByUsernameAndPassword(String username, String password);
//	Operator findByUsername(String username);	
	
}