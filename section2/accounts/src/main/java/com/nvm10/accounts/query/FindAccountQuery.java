package com.nvm10.accounts.query;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class FindAccountQuery {

    @TargetAggregateIdentifier
    public final String mobileNumber;
}
