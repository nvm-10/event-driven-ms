package com.nvm10.accounts.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteAccountCommand {

    @TargetAggregateIdentifier
    private Long accountNumber;
    private boolean activeSw;
}
