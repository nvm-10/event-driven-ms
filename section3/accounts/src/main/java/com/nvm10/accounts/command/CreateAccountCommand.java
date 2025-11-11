package com.nvm10.accounts.command;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Jacksonized
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private String mobileNumber;

}
