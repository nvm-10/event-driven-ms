package com.nvm10.customer.command;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Jacksonized
public class DeleteCustomerCommand {

    @TargetAggregateIdentifier
    private final String customerId;
    private final boolean activeSw;
}
