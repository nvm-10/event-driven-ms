package com.nvm10.customer.saga;

import com.nvm10.common.command.*;
import com.nvm10.common.event.*;
import com.nvm10.customer.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Saga
@Slf4j
public class UpdateMobileNumberSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryUpdateEmitter emitter;
    @Autowired
    private QueryUpdateEmitter queryUpdateEmitter;

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
                    log.error("Saga Event 1 : Failed to process UpdateAccountMobileNumberCommand for Customer ID: {}",
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

    @SagaEventHandler(associationProperty = "customerId")
    public void on(AccountMobileNumberUpdatedEvent event) {
        log.info("Saga Event 2 : Recieved AccountMobileNumberUpdatedEvent for Account Number: {}",
                event.getAccountNumber());
        UpdateCardMobileNumberCommand command = UpdateCardMobileNumberCommand.builder()
                .customerId(event.getCustomerId())
                .currentMobileNumber(event.getCurrentMobileNumber())
                .newMobileNumber(event.getNewMobileNumber())
                .accountNumber(event.getAccountNumber())
                .cardNumber(event.getCardNumber())
                .loanNumber(event.getLoanNumber())
                .build();
        commandGateway.send(command, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateCardMobileNumberCommand> commandMessage,
                                 @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    log.error("Saga Event 2 : Failed to process UpdateCardMobileNumberCommand for Account Number: {}",
                            event.getAccountNumber(), commandResultMessage.exceptionResult());
                    RollbackAccountMobileNumberCommand rollbackAccountMobileNumberCommand = RollbackAccountMobileNumberCommand.builder()
                            .customerId(event.getCustomerId())
                            .accountNumber(event.getAccountNumber())
                            .currentMobileNumber(event.getCurrentMobileNumber())
                            .newMobileNumber(event.getNewMobileNumber())
                            .errorMsg(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.sendAndWait(rollbackAccountMobileNumberCommand);
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void on(CardMobileNumberUpdatedEvent event) {
        log.info("Saga Event 3 : Recieved CardMobileNumberUpdatedEvent for cardNumber Number: {}",
                event.getCardNumber());
        UpdateLoanMobileNumberCommand command = UpdateLoanMobileNumberCommand.builder()
                .customerId(event.getCustomerId())
                .currentMobileNumber(event.getCurrentMobileNumber())
                .newMobileNumber(event.getNewMobileNumber())
                .accountNumber(event.getAccountNumber())
                .cardNumber(event.getCardNumber())
                .loanNumber(event.getLoanNumber())
                .build();
        commandGateway.send(command, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateLoanMobileNumberCommand> commandMessage,
                                 @Nonnull CommandResultMessage<?> commandResultMessage) {
                log.error("Saga Event 3 : Completed processing UpdateLoanMobileNumberCommand for cardNumber Number: {}",
                        event.getCardNumber());
                RollbackCardMobileNumberCommand command = RollbackCardMobileNumberCommand.builder()
                        .customerId(event.getCustomerId())
                        .accountNumber(event.getAccountNumber())
                        .cardNumber(event.getCardNumber())
                        .currentMobileNumber(event.getCurrentMobileNumber())
                        .newMobileNumber(event.getNewMobileNumber())
                        .errorMsg(commandResultMessage.exceptionResult().getMessage())
                        .build();
                commandGateway.sendAndWait(command);
            }
        });
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void on(LoanMobileNumberUpdatedEvent event) {
        log.info("Saga Event 4 [End] : Recieved LoanMobileNumberUpdatedEvent for Loan Number: {}",
                event.getLoanNumber());
        queryUpdateEmitter.emit(
                UpdateMobileNumberSaga.class, query -> true,
                new ResponseDto("200", "Mobile number updated successfully")
        );
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void on(CardMobileNumberRollbackedEvent event) {
        log.info("Saga Rollback 1 : Recieved CardMobileNumberRollbackedEvent for card number: {}",
                event.getCardNumber());
        RollbackAccountMobileNumberCommand command = RollbackAccountMobileNumberCommand.builder()
                .customerId(event.getCustomerId())
                .accountNumber(event.getAccountNumber())
                .currentMobileNumber(event.getCurrentMobileNumber())
                .newMobileNumber(event.getNewMobileNumber())
                .errorMsg(event.getErrorMsg())
                .build();
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void on(AccountMobileNumberRollbackedEvent event) {
        log.info("Saga Rollback 2 : Recieved AccountMobileNumberRollbackedEvent for account number: {}",
                event.getAccountNumber());
        RollbackCustomerMobileNumberCommand command = RollbackCustomerMobileNumberCommand.builder()
                .customerId(event.getCustomerId())
                .currentMobileNumber(event.getCurrentMobileNumber())
                .newMobileNumber(event.getNewMobileNumber())
                .errorMsg(event.getErrorMsg())
                .build();
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void on(CustomerMobileNumberRollbackedEvent event) {
        log.info("Saga Rollback 3 [End] : Recieved CustomerMobileNumberRollbackedEvent for customer ID: {}",
                event.getCustomerId());
        queryUpdateEmitter.emit(
                UpdateMobileNumberSaga.class, query -> true,
                new ResponseDto("500", "Mobile number updated failed")
        );
    }
}
