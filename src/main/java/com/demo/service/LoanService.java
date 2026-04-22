package com.demo.service;

import com.demo.model.Loan;
import com.demo.dto.LoanApplyRequest;
import java.util.List;

public interface LoanService {

	Loan applyLoan(LoanApplyRequest request, String userEmail);

	List<Loan> getAllLoans();

	List<Loan> getLoansByUser(String email);

	Loan updateStatus(Long id, String status);

	Loan getLoanById(Long id);
}