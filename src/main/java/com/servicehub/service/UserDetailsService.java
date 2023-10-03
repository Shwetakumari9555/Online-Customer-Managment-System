package com.servicehub.service;

import java.util.*;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.servicehub.model.Login;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.repository.LoginRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(LoginRepository.class);
    private LoginRepository loginRepository;

    public UserDetailsService(LoginRepository loginRepository) {
        super();
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername of UserDetailsService");
        log.info("Username to load: " + username);

        Optional<com.servicehub.model.Login> opt = loginRepository.findByUsername(username);
        logger.info("found login Entity in loginRepo with" + username +opt);
        if (opt.isPresent()) {
            Login login = opt.get();

            // List to store roles
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Add roles based on UserType
            switch (login.getType()) {
                case ADMIN:
                    authorities.add(new SimpleGrantedAuthority("ADMIN"));
                    logger.info("Settting Authority" + username +opt);
                    break;
                case USER:
                    authorities.add(new SimpleGrantedAuthority("USER"));
                    break;
                default:
                    authorities.add(new SimpleGrantedAuthority("OPERATOR"));
            }
            
            User user = new User(login.getUsername(), login.getPassword(), authorities);
            logger.info("Creating User of UserDetailsService" + login.getUsername()+authorities);
            return user;
            
        } else {
            throw new BadCredentialsException("Customer Details not found with this username: " + username);
        }
    }

}




