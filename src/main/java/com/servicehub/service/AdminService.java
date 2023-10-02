package com.servicehub.service;


import java.util.List;

import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.DepartmentAlreadyExistsException;
import com.servicehub.exception.DepartmentNotFoundException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperatorAlreadyExistsException;
import com.servicehub.model.Department;
import com.servicehub.model.Operator;

public interface AdminService {

    boolean addDepartment(Department department) throws DatabaseOperationException, DepartmentAlreadyExistsException;

    boolean removeDepartment(int departmentId) throws DatabaseOperationException;

    Department modifyDepartment(Department department) throws DepartmentNotFoundException, DatabaseOperationException;

    Department findDepartmentById(int departmentId) throws DepartmentNotFoundException, DatabaseOperationException;

    boolean addOperator(Operator operator) throws OperatorAlreadyExistsException, DatabaseOperationException;

    boolean removeOperator(int operatorId) throws DatabaseOperationException;

    Operator modifyOperator(Operator operator) throws NotFoundException ,DatabaseOperationException ;

    Operator findOperatorById(int operatorId) throws DatabaseOperationException, NotFoundException;

    List<Operator> findAllOperators() throws DatabaseOperationException;

//	List<Operator> findAllOperators(int page, int pageSize) throws DatabaseOperationException;
}

