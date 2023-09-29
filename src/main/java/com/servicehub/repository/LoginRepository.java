package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Login;


public interface LoginRepository extends JpaRepository<Login, Integer>{

	Login findByUsername(String username);

}
