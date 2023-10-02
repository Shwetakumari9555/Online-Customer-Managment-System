package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.servicehub.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
    

	boolean existsByDepartmentName(String departmentName);

}