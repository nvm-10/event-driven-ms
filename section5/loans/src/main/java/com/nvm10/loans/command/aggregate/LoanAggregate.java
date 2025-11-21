package com.nvm10.loans.command.aggregate;

import com.nvm10.common.command.UpdateLoanMobileNumberCommand;
import com.nvm10.common.event.CardMobileNumberUpdatedEvent;
import com.nvm10.loans.command.CreateLoanCommand;
import com.nvm10.loans.command.DeleteLoanCommand;
import com.nvm10.loans.command.UpdateLoanCommand;
import com.nvm10.loans.command.event.LoanCreatedEvent;
import com.nvm10.loans.command.event.LoanDeletedEvent;
import com.nvm10.loans.command.event.LoanUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class LoanAggregate {

    private Long loanNumber;
    @TargetAggregateIdentifier
    private String mobileNumber;
    private String loanType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
    private boolean activeSw;

    public LoanAggregate() {}

    @CommandHandler
    public LoanAggregate(CreateLoanCommand command) {
        LoanCreatedEvent event = new LoanCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(LoanCreatedEvent event) {
        this.mobileNumber = event.getMobileNumber();
    }

    @CommandHandler
    public void on(UpdateLoanCommand command) {
        LoanUpdatedEvent event = new LoanUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(LoanUpdatedEvent event) {
        this.loanNumber = event.getLoanNumber();
        this.mobileNumber = event.getMobileNumber();
        this.loanType = event.getLoanType();
        this.totalLoan = event.getTotalLoan();
        this.amountPaid = event.getAmountPaid();
        this.outstandingAmount = event.getOutstandingAmount();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void on(DeleteLoanCommand command) {
        LoanDeletedEvent event = new LoanDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(UpdateLoanMobileNumberCommand command) {
        CardMobileNumberUpdatedEvent event = new CardMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CardMobileNumberUpdatedEvent event) {
        this.mobileNumber = event.getNewMobileNumber();
    }
}
