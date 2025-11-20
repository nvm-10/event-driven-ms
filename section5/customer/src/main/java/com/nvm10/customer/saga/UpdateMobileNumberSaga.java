package com.nvm10.customer.saga;

import com.nvm10.common.command.RollbackCustomerMobileNumberCommand;
import com.nvm10.common.command.UpdateAccountMobileNumberCommand;
import com.nvm10.common.event.CustomerMobileNumberUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.annotation.Nonnull;

@Saga
@Slf4j
public class UpdateMobileNumberSaga {

    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void on(CustomerMobileNumberUpdatedEvent event) {
        log.info("Saga Event 1 [Start] : Recieved CustomerMobileNumberUpdatedEvent for Customer ID: {}",
                event.getCustomerId());

        UpdateAccountMobileNumberCommand command = UpdateAccountMobileNumberCommand.builder()
                .customerId(event.getCustomerId())
                .currentMobileNumber(event.getCurrentMobileNumber())
                .newMobileNumber(event.getNewMobileNumber())
                .accountNumber(event.getAccountNumber())
                .cardNumber(event.getCardNumber())
                .loanNumber(event.getLoanNumber())
                .build();
        commandGateway.send(command, new CommandCallback<>() {

            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateAccountMobileNumberCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    log.error("Saga Event 1 [End] : Failed to process UpdateAccountMobileNumberCommand for Customer ID: {}",
                            event.getCustomerId(), commandResultMessage.exceptionResult());
                    RollbackCustomerMobileNumberCommand rollbackCustomerMobileNumberCommand = RollbackCustomerMobileNumberCommand.builder()
                            .customerId(event.getCustomerId())
                            .currentMobileNumber(event.getCurrentMobileNumber())
                            .newMobileNumber(event.getNewMobileNumber())
                            .errorMsg(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.sendAndWait(rollbackCustomerMobileNumberCommand);
                }
            }
        });
    }
}
