package com.nvm10.loans.query.handler;

import com.nvm10.loans.dto.LoansDto;
import com.nvm10.loans.query.FindLoanQuery;
import com.nvm10.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanQueryHandler {

    private final ILoansService loansService;

    @QueryHandler
    public LoansDto fetchLoan(FindLoanQuery query) {
        return loansService.fetchLoan(query.getMobileNumber());
    }
}
