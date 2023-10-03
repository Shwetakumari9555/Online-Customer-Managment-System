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
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueld;
    
    @NotBlank(message = "Issue type cannot be blank")
    private String issueType;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Enumerated(EnumType.STRING)
    private Issue_Status status;

    @ManyToMany
    @JoinTable(
        name = "operator_issue",
        joinColumns = @JoinColumn(name = "issue_id"),
        inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    
    @JsonIgnore
    private List<Operator> operators;

    @OneToOne(mappedBy = "issue")
    @JsonIgnore
    private Solution solution;

	public Issue(int issueld, @NotBlank(message = "Issue type cannot be blank") String issueType,
			@NotBlank(message = "Description cannot be blank") String description, Issue_Status status,
			List<Operator> operators, Solution solution) {
		super();
		this.issueld = issueld;
		this.issueType = issueType;
		this.description = description;
		this.status = status;
		this.operators = operators;
		this.solution = solution;
	}

	
	
    
    
    
    
    
    
}

