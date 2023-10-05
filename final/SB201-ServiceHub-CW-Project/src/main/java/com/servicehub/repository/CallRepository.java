package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Call;

public interface CallRepository extends JpaRepository<Call, Integer>{
	

}
