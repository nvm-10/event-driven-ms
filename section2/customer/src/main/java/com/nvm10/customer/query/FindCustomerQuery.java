package com.nvm10.customer.query;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class FindCustomerQuery {

    @TargetAggregateIdentifier
    public final String mobileNumber;
}
