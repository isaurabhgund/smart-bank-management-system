package com.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.demo.model.Loan;
import com.demo.dto.LoanApplyRequest;
import com.demo.dto.LoanResponse;
import com.demo.service.LoanService;

@RestController
@RequestMapping("/api/v1/loans")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> applyLoan(@Valid @RequestBody LoanApplyRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Loan loan = loanService.applyLoan(request, email);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @GetMapping("/me")
    public ResponseEntity<List<LoanResponse>> getMyLoans() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<LoanResponse> loans = loanService.getLoansByUser(email).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    private LoanResponse mapToResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setLoanType(loan.getLoanType());
        response.setAmount(loan.getAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTenure(loan.getTenure());
        response.setStatus(loan.getStatus());
        response.setEmi(loan.getEmi());
        response.setTotalAmount(loan.getTotalAmount());
        response.setTotalInterest(loan.getTotalInterest());
        response.setCreatedAt(loan.getCreatedAt());
        response.setUserEmail(loan.getUser().getEmail());
        return response;
    }
}