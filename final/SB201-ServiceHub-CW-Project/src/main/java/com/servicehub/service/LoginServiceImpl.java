package com.servicehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicehub.exception.NotFoundException;
import com.servicehub.model.Login;
import com.servicehub.repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginRepository loginRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public Customer authenticate(String username, String password) {
//        // Find the Login entity by username
//        Optional<Login> login = loginRepository.findByUsername(username);
//
//        if (login != null && passwordEncoder.matches(password, login.get().getPassword())) {
//            // Password matches, return the associated Customer
//            return login.get().getCustomer();
//        }
//
//        // Authentication failed
//        return null;
//    }
    
    @Override
    public Login findByUsername(String usrname) throws NotFoundException{
    	return loginRepository.findByUsername(usrname).orElseThrow(() -> new NotFoundException("Customer Not found with Username: "+usrname));
    }
}
