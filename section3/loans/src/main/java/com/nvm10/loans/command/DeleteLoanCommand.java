package com.nvm10.loans.command;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Jacksonized
public class DeleteLoanCommand {

    @TargetAggregateIdentifier
    private Long loanNumber;
    private boolean activeSw;
}
