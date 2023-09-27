package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerd;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "[6-9][0-9]{9}", message="Invaid mobile number")
    private String mobile;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @OneToMany(mappedBy = "customer")
    private List<Call> calls;

    public Customer(String firstName, String lastName, String email, String mobile, String city, List<Call> calls) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.calls = calls;
    }
}


