package com.servicehub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicehub.exception.NotFoundException;
import com.servicehub.model.Login;
import com.servicehub.service.LoginService;


@RestController
public class LogInController {
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    


//  @GetMapping("/logIn")
//  public ResponseEntity<String> authenticate(Authentication authentication) {
//      try {
//          Login login = loginService.findByUsername(authentication.getName());
//          if (login != null) {
//              // Authentication is successful, generate and return the token in the header
//              // You can return a success message or any other relevant data in the response body
//              return ResponseEntity.ok().body("Authentication successful");
//          } else {
//              // Authentication failed
//              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//          }
//      } catch (Exception e) {
//          // Log the exception for debugging
//          e.printStackTrace();
//          // Handle the exception and return an appropriate response
//          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
//      }
//  }



}

