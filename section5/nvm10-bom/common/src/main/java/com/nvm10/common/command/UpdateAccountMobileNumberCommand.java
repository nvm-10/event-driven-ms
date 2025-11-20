package com.nvm10.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateAccountMobileNumberCommand {

    private final String customerId;
    private final String currentMobileNumber;
    private final String newMobileNumber;
    @TargetAggregateIdentifier
    private final Long accountNumber;
    private final Long cardNumber;
    private final Long loanNumber;
}
