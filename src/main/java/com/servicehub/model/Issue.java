package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueld;
    
    @NotBlank(message = "Issue type cannot be blank")
    private String issueType;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Issue status cannot be blank")
    private String issueStatus;

    @ManyToMany
    @JoinTable(
        name = "operator_issue",
        joinColumns = @JoinColumn(name = "issue_id"),
        inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    private List<Operator> operators;

    @OneToOne(mappedBy = "issue")
    private Solution solution;

	public Issue(String issueType, String description, String issueStatus, List<Operator> operators,
			Solution solution) {
		super();
		this.issueType = issueType;
		this.description = description;
		this.issueStatus = issueStatus;
		this.operators = operators;
		this.solution = solution;
	}
    
    
    
    
    
    
}

