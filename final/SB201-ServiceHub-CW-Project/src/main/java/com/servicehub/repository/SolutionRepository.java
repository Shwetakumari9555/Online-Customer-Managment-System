package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Integer>{

}