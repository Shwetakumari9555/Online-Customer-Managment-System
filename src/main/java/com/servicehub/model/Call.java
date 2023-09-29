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
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int callid;

    @NotNull(message = "Call date cannot be null")
    @Past(message = "Call date should be in the past")
    private Date callDate;

    @PositiveOrZero(message = "Call duration should be a positive number or zero")
    private double callDuration;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "[6-9][0-9]{9}", message="Invaid mobile number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Call(Date callDate, double callDuration, String phoneNumber, Operator operator, Issue issue,
                Customer customer) {
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.phoneNumber = phoneNumber;
        this.operator = operator;
        this.issue = issue;
        this.customer = customer;
    }
}


