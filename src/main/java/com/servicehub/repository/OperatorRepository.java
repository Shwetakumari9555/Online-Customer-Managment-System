package com.servicehub.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.servicehub.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {
	@Query("SELECT c FROM Operator c WHERE c.login.username = :username")
	boolean existsByUsername(String username);
    
	@Query("SELECT o FROM Operator o WHERE o.login.username = :username")
	Optional<Operator> findOperatorByUsername(String username);
	
//
//	Page<Operator> findAll(Pageable pageable);

//	Operator findByUsernameAndPassword(String username, String password);
//	Operator findByUsername(String username);	
	
}