package com.servicehub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    
    @Enumerated(EnumType.STRING)
    private UserType type;

    private boolean isActive;
    
    @JsonIgnore
    @OneToOne(mappedBy = "login")
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "login")
    private Operator operator;
}


