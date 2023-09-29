package com.servicehub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Login {

    public enum UserType {
        ADMIN, USER, OPERATOR
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loginld;

    @NotBlank(message = "Password cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, message = "Password should have at least 4 characters")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private UserType type;

    private boolean isActive;
    
    @OneToOne(mappedBy = "login")
    private Customer customer;

    @OneToOne(mappedBy = "login")
    private Operator operator;
}


