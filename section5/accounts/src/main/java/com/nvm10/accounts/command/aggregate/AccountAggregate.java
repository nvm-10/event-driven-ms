package com.nvm10.accounts.command.aggregate;

import com.nvm10.accounts.command.CreateAccountCommand;
import com.nvm10.accounts.command.DeleteAccountCommand;
import com.nvm10.accounts.command.UpdateAccountCommand;
import com.nvm10.accounts.command.event.AccountCreatedEvent;
import com.nvm10.accounts.command.event.AccountDeletedEvent;
import com.nvm10.accounts.command.event.AccountUpdatedEvent;
import com.nvm10.common.command.UpdateAccountMobileNumberCommand;
import com.nvm10.common.event.AccountMobileNumberUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String mobileNumber;
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private boolean activeSw;

    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AccountCreatedEvent event = new AccountCreatedEvent();
        BeanUtils.copyProperties(createAccountCommand, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.mobileNumber = event.getMobileNumber();
        this.accountNumber = event.getAccountNumber();
        this.accountType = event.getAccountType();
        this.branchAddress = event.getBranchAddress();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void on(UpdateAccountCommand command) {
        AccountUpdatedEvent event = new AccountUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountUpdatedEvent event) {
        this.accountType = event.getAccountType();
        this.branchAddress = event.getBranchAddress();
        this.mobileNumber = event.getMobileNumber();
    }

    @CommandHandler
    public void on(DeleteAccountCommand command) {
        AccountDeletedEvent event = new AccountDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountDeletedEvent event) {
        this.accountNumber = event.getAccountNumber();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void on(UpdateAccountMobileNumberCommand command) {
        AccountMobileNumberUpdatedEvent event = new AccountMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountMobileNumberUpdatedEvent event) {
        this.mobileNumber = event.getNewMobileNumber();
    }
}
