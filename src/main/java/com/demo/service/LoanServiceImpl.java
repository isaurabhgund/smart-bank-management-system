package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Loan;
import com.demo.model.User;
import com.demo.dto.LoanApplyRequest;
import com.demo.repository.LoanRepository;
import com.demo.repository.UserRepository;
import com.demo.exception.ResourceNotFoundException;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Loan applyLoan(LoanApplyRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setLoanType(request.getLoanType());
        loan.setAmount(request.getAmount());
        loan.setTenure(request.getTenure());
        loan.setStatus("PENDING");

        // Set interest rate based on loan type
        double interestRate = switch (request.getLoanType().toUpperCase()) {
            case "HOME" -> 8.5;
            case "PERSONAL" -> 14.0;
            case "AUTO" -> 9.5;
            default -> 12.0;
        };
        loan.setInterestRate(interestRate);

        calculateEMI(loan);

        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }

    @Override
    public List<Loan> getLoansByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return loanRepository.findByUserId(user.getId());
    }

    @Override
    public Loan updateStatus(Long loanId, String status) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));
        loan.setStatus(status);
        return loanRepository.save(loan);
    }

    private void calculateEMI(Loan loan) {
        double p = loan.getAmount();
        double annualRate = loan.getInterestRate();
        int n = loan.getTenure();

        double r = annualRate / (12 * 100);

        double emi = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
        
        loan.setEmi(Math.round(emi * 100.0) / 100.0);
        loan.setTotalAmount(Math.round((emi * n) * 100.0) / 100.0);
        loan.setTotalInterest(Math.round(((emi * n) - p) * 100.0) / 100.0);
    }
}