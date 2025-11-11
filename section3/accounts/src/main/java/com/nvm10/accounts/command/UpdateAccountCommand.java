package com.nvm10.accounts.command;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Jacksonized
public class UpdateAccountCommand {

    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    @TargetAggregateIdentifier
    private String mobileNumber;
    private boolean activeSw;
}
