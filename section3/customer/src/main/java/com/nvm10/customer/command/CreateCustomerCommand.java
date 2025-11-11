package com.nvm10.customer.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Jacksonized
public class CreateCustomerCommand {

    @TargetAggregateIdentifier
    private final String customerId;
    private final String name;
    private final String email;
    private final String mobileNumber;
    private final boolean activeSw;

}
