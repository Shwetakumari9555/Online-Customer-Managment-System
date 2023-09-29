package com.servicehub.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	Admin findByUsernameAndPassword(String username, String password);
	 Admin findByUsername(String username);
	 
}