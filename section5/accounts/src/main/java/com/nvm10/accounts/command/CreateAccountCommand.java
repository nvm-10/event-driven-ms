package com.nvm10.accounts.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private final Long accountNumber;
    private final String accountType;
    private final String branchAddress;
    private final String mobileNumber;
    private final boolean activeSw;

}
