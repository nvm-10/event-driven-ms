package com.nvm10.cards.command;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Jacksonized
public class UpdateCardCommand {

    private Long cardNumber;
    @TargetAggregateIdentifier
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
    private boolean activeSw;
}
