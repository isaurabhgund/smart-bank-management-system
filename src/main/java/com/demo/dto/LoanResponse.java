package com.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LoanResponse {
    private Long id;
    private String loanType;
    private Double amount;
    private Double interestRate;
    private Integer tenure;
    private String status;
    private Double emi;
    private Double totalAmount;
    private Double totalInterest;
    private LocalDateTime createdAt;
    private String userEmail;
}
