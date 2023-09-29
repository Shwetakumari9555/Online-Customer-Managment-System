package com.servicehub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int solutionld;

    @NotBlank(message = "Solution description cannot be blank")
    private String solutionDescription;

    @NotNull(message = "Solution date cannot be null")
    @PastOrPresent(message = "Solution date should be in the past or present")
    private Date solutionDate;

    @OneToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    public Solution(String solutionDescription, Date solutionDate, Issue issue, Operator operator) {
        this.solutionDescription = solutionDescription;
        this.solutionDate = solutionDate;
        this.issue = issue;
        this.operator = operator;
    }
}


