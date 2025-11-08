package com.nvm10.loans.query;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class FindLoanQuery {

    @TargetAggregateIdentifier
    private String mobileNumber;
}
