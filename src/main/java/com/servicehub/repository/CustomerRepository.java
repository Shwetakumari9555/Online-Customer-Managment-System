package com.servicehub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicehub.model.Customer;
import com.servicehub.model.Login;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query("SELECT c FROM Customer c WHERE c.login.username = :username")
    Optional<Customer> findCustomerByUsername(@Param("username") String username);
	
//	 Optional <Customer> findByUsername(String usename);
//	 Customer findByMobile(String mobile);
//	 List<Customer> findCustomerByfirstName(String name);
//	 Customer findByUsernameAndPassword(String username, String password);
//	 Optional<Customer>  findByEmail(String email);

}
