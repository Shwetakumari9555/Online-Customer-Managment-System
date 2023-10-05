package com.servicehub.service;


import java.util.List;

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DepartmentAlreadyExistsException;
import com.servicehub.exception.DepartmentNotFoundException;
import com.servicehub.exception.DuplicateValueException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorAlreadyExistsException;
import com.servicehub.model.Department;
import com.servicehub.model.Operator;

public interface AdminService {

    boolean addDepartment(Department department) throws DatabaseOperationException, DepartmentAlreadyExistsException;

//    public String removeDepartment(int departmentId) throws DatabaseOperationException;

    Department modifyDepartment(Department department, Integer departmentId) throws DepartmentNotFoundException;

    Department findDepartmentById(Integer departmentId) throws DepartmentNotFoundException;

    public Operator addOperator(Operator operator) throws  DuplicateValueException ;

   

    public Operator modifyOperator(Operator operator, Integer operatorId) throws DatabaseOperationException;

    Operator findOperatorById(Integer operatorId) throws  NotFoundException;

    List<Operator> findAllOperators() throws NotFoundException;

	String removeOperator(Integer operatorId) throws DatabaseOperationException;

	String removeDepartment(Integer departmentId) throws  DepartmentNotFoundException;

//	List<Operator> findAllOperators(int page, int pageSize) throws DatabaseOperationException;
}

