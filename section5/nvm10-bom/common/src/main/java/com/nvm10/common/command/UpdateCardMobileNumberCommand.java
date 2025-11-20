package com.nvm10.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateCardMobileNumberCommand {

    private final String customerId;
    private final String currentMobileNumber;
    private final String newMobileNumber;
    private final Long accountNumber;
    @TargetAggregateIdentifier
    private final Long cardNumber;
    private final Long loanNumber;
}
