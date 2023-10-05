package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "operators")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operatorld;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "you must provide the operator email")
	@Email
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "[6-9][0-9]{9}", message="Invaid mobile number")
    private String mobile;

    @NotBlank(message = "City cannot be blank")
    private String city;
    
    @JsonIgnore
    @OneToMany(mappedBy = "operator")
    private List<Call> calls;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "operators")
    private List<Issue> issues;
    
    @OneToOne
    @JoinColumn(name = "login_id")
//    @JsonIgnore
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

	public Operator(int operatorld, @NotBlank(message = "First name cannot be blank") String firstName,
			@NotBlank(message = "Last name cannot be blank") String lastName,
			@NotBlank(message = "you must provide the operator email") @Email String email,
			@NotBlank(message = "Mobile number cannot be blank") @Pattern(regexp = "[6-9][0-9]{9}", message = "Invaid mobile number") String mobile,
			@NotBlank(message = "City cannot be blank") String city) {
		super();
		this.operatorld = operatorld;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.city = city;
	}
    
    
}


