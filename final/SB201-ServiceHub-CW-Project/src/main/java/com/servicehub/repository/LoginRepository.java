package com.servicehub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicehub.model.Login;


public interface LoginRepository extends JpaRepository<Login, Integer>{

    @Query("SELECT l FROM Login l WHERE l.username = :username")
    Optional<Login> findByUsername(@Param("username") String username);


}
