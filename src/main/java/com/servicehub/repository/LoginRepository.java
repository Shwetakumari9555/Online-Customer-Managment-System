package com.servicehub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Login;

public interface LoginRepository extends JpaRepository<Login, Integer>{
	
    public Optional<Login> findByUsername(String username);
}
