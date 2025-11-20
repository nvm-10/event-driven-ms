package com.nvm10.accounts.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateAccountCommand {

    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    @TargetAggregateIdentifier
    private String mobileNumber;
    private boolean activeSw;
}
