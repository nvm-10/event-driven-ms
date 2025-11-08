package com.nvm10.accounts.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private String mobileNumber;

}
