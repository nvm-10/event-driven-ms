package com.nvm10.loans.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateLoanCommand {

    @TargetAggregateIdentifier
    private final Long loanNumber;
    private final String mobileNumber;
    private final String loanType;
    private final String loanStatus;
    private final int totalLoan;
    private final int amountPaid;
    private final int outstandingAmount;
    private final boolean activeSw;

}
