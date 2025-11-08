package com.nvm10.loans.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateLoanCommand {

    @TargetAggregateIdentifier
    private String mobileNumber;
}
