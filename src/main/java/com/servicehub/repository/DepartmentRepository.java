package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}