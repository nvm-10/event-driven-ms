package com.nvm10.cards.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@Builder
public class DeleteCardCommand {

    @AggregateIdentifier
    private Long cardNumber;
    private boolean activeSw;
}
