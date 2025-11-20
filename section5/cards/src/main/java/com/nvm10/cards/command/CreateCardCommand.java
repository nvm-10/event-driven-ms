package com.nvm10.cards.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@Builder
public class CreateCardCommand {

    @AggregateIdentifier
    private String mobileNumber;
}
