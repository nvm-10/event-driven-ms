package com.nvm10.profile.query;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class FindProfileQuery {

    @TargetAggregateIdentifier
    public final String mobileNumber;
}
