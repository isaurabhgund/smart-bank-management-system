package com.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanApplyRequest {

    @NotBlank(message = "Loan type is required")
    private String loanType;

    @NotNull(message = "Amount is required")
    @Min(value = 1000, message = "Amount must be at least 1000")
    private Double amount;

    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    private Integer tenure;
}
