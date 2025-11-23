package com.nvm10.loans.query.projection;

import com.nvm10.common.event.LoanMobileNumberUpdatedEvent;
import com.nvm10.loans.command.event.LoanCreatedEvent;
import com.nvm10.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanProjection {

    private final ILoansService loansService;

    @EventHandler
    public void on(LoanCreatedEvent event) {
        loansService.createLoan(event.getMobileNumber());
    }

    @EventHandler
    public void on(LoanMobileNumberUpdatedEvent event) {
        loansService.updateLoanMobileNumber(event.getCurrentMobileNumber(), event.getNewMobileNumber());
    }

}
