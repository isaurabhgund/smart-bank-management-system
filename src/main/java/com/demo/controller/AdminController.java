package com.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.model.Loan;
import com.demo.dto.LoanResponse;
import com.demo.service.LoanService;

@RestController
@RequestMapping("/api/v1/admin/loans")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        List<LoanResponse> loans = loanService.getAllLoans().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loans);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LoanResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Loan loan = loanService.updateStatus(id, status);
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