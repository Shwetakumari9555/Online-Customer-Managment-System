package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern.Flag;
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

    @NotBlank(message = "you must provide the customer email")
	@Pattern(regexp = "[a-z0-9.]+@[a-z0-9.]+\\\\.[a-z] {2,3}", flags = Flag.CASE_INSENSITIVE,
			message="Invaid email id")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "[6-9][0-9]{9}", message="Invaid mobile number")
    private String mobile;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @OneToMany(mappedBy = "customer")
    private List<Call> calls;
    
    @OneToOne
    @JoinColumn(name = "login_id")
    private Login login;

    public Customer(String firstName, String lastName, String email, String mobile, String city, List<Call> calls) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.calls = calls;
    }
}


