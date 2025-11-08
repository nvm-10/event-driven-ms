package com.nvm10.cards.query;

import lombok.Value;
import org.axonframework.modelling.command.AggregateIdentifier;

@Value
public class FindCardQuery {

    @AggregateIdentifier
    private String mobileNumber;
}
