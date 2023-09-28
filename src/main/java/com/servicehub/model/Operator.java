package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import jakarta.validation.constraints.Pattern.Flag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "operator")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operatorld;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;


    @NotBlank(message = "you must provide the operator email")
	  @Pattern(regexp = "[a-z0-9.]+@[a-z0-9.]+\\\\.[a-z] {2,3}", flags = Flag.CASE_INSENSITIVE,
			message="Invaid email id")

    @Email(message = "Invalid email format")

    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "[6-9][0-9]{9}", message="Invaid mobile number")
    private String mobile;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @OneToMany(mappedBy = "operator")
    private List<Call> calls;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "operators")
    private List<Issue> issues;

    
    @OneToOne
    @JoinColumn(name = "login_id")
    private Login login;


    public Operator(String firstName, String lastName, String email, String mobile, String city, List<Call> calls,
                    Department department, List<Issue> issues) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.calls = calls;
        this.department = department;
        this.issues = issues;
    }
}


