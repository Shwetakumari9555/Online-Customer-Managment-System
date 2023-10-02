package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;
    
    @NotBlank(message = "Department name cannot be blank")
    private String departmentName;
    
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Operator> operators;

	public Department(String departmentName, List<Operator> operators) {
		super();
		this.departmentName = departmentName;
		this.operators = operators;
	}

    
}

