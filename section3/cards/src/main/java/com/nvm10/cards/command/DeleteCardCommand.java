package com.nvm10.cards.command;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@Builder
@Jacksonized
public class DeleteCardCommand {

    @AggregateIdentifier
    private Long cardNumber;
    private boolean activeSw;
}
