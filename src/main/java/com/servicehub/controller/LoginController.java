package com.servicehub.controller;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/customer")
    public ResponseEntity<String> customerLogin(
        @RequestParam String username,
        @RequestParam String password
    ) {
        try {
            Authentication auth = authenticationService.authenticateCustomer(username, password);
            return new ResponseEntity<>(auth.getName() + " Logged In Successfully", HttpStatus.ACCEPTED);
        } catch (LoginException e) {
            return new ResponseEntity<>("Customer Login Failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/operator")
    public ResponseEntity<String> operatorLogin(
        @RequestParam String username,
        @RequestParam String password
    ) {
        try {
            Authentication auth = authenticationService.authenticateOperator(username, password);
            return new ResponseEntity<>(auth.getName() + " Logged In Successfully", HttpStatus.ACCEPTED);
        } catch (LoginException e) {
            return new ResponseEntity<>("Operator Login Failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}