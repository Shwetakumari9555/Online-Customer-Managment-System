package com.servicehub.controller;

import java.util.*;

import com.servicehub.appconfig.SecurityConstants;
import com.servicehub.dto.LoginResDto;
import com.servicehub.model.Customer;

import com.servicehub.service.UserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicehub.exception.CustomerException;
import com.servicehub.exception.DatabaseOperationException;
import com.servicehub.exception.NotFoundException;
import com.servicehub.exception.OperationFailedException;
import com.servicehub.exception.OperatorException;
import com.servicehub.model.Customer;
import com.servicehub.model.Issue;
import com.servicehub.repository.CustomerRepository;
import com.servicehub.service.OperatorService;

import jakarta.validation.Valid;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/operator")
public class  OperatorController {
    @Autowired UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
	private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
	
	@Autowired
	private OperatorService operatorService;
	
	
	@GetMapping("/logIn")
	public ResponseEntity<String> logInUserHandler(Authentication auth) throws OperatorException, NotFoundException {
	    String username = auth.getName(); // Get the username from authentication
	    
	    logger.info("Logged in Operator: {}", username);

	    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

	    return ResponseEntity.ok( authorities +":"+ username + " Logged In Successfully");
	}

    @PostMapping("/accessToken")
    public ResponseEntity<LoginResDto> getAccessToken(@RequestBody LoginDTO loginDTO) throws OperatorException, NotFoundException {
        User user = (User) userDetailsService.loadUserByUsername(loginDTO.getUsername());
        if(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

            String jwt = Jwts.builder()
                    .setIssuer("Ram")
                    .setSubject("JWT Token")
                    .claim("username", user.getUsername())
                    .claim("authorities", populateAuthorities(user.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+ 30000000)) // millisecond expiration time of around 8 hours
                    .signWith(key).compact();
            LoginResDto loginResDto = new LoginResDto();
            loginResDto.setAccessToken(jwt);
            return ResponseEntity.ok(loginResDto);
        }else{
            throw new BadCredentialsException("Invalid credentials");
        }

    }
    


	@PostMapping("/addCustomerIssue")
    public ResponseEntity<Issue> addCustomerIssue(@Valid @RequestBody Issue issue) throws DatabaseOperationException {
        Issue addedIssue = operatorService.addCustomerIssue(issue);
        return ResponseEntity.ok(addedIssue);
    }

    @PostMapping("/sendIntimationEmailToCustomer/{customerId}/{issueId}")
    public ResponseEntity<String> sendIntimationEmailToCustomer(
            @PathVariable int customerId,
            @PathVariable int issueId) throws NotFoundException, OperationFailedException {
        String result = operatorService.sendIntimationEmailToCustomer(customerId, issueId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sendModificationEmailToCustomer/{customerId}/{issueId}")
    public ResponseEntity<String> sendModificationEmailToCustomer(
            @PathVariable int customerId,
            @PathVariable int issueId) throws OperationFailedException, NotFoundException {
        String result = operatorService.sendModificationEmailToCustomer(customerId, issueId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/closeCustomerIssue")
    public ResponseEntity<Issue> closeCustomerIssue( @RequestBody Issue issue ,@PathVariable Integer issueId) throws DatabaseOperationException, NotFoundException {
        Issue closedIssue = operatorService.closeCustomerIssue(issue , issueId);
        return ResponseEntity.ok(closedIssue);
    }

    @GetMapping("/findCustomerByName/{customerName}")
    public ResponseEntity<List<Customer>> findCustomerByName(@PathVariable String customerName) throws DatabaseOperationException {
        List<Customer> customers = operatorService.findCustomerByName(customerName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/findCustomerById/{customerId}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable int customerId) throws DatabaseOperationException, NotFoundException {
        Customer customer = operatorService.findCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/findCustomerByEmail/{customerEmail}")
    public ResponseEntity<Customer> findCustomerByEmail(@PathVariable String customerEmail) throws DatabaseOperationException, NotFoundException {
        Customer customer = operatorService.findCustomerByEmail(customerEmail);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/lockCustomer/{customerId}")
    public ResponseEntity<Boolean> lockCustomer(@PathVariable int customerId) throws DatabaseOperationException, NotFoundException {
        boolean result = operatorService.lockCustomer(customerId);
        return ResponseEntity.ok(result);
    }



    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {

        Set<String> authoritiesSet = new HashSet<>();

        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);


    }
}
