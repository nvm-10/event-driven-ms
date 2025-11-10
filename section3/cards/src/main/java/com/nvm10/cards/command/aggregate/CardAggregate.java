package com.nvm10.cards.command.aggregate;

import com.nvm10.cards.command.CreateCardCommand;
import com.nvm10.cards.command.DeleteCardCommand;
import com.nvm10.cards.command.UpdateCardCommand;
import com.nvm10.cards.command.event.CardCreatedEvent;
import com.nvm10.cards.command.event.CardDeletedEvent;
import com.nvm10.cards.command.event.CardUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class CardAggregate {

    private Long cardNumber;
    @AggregateIdentifier
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
    private boolean activeSw;

    public CardAggregate() {}

    @CommandHandler
    public CardAggregate(CreateCardCommand createCommand) {
        CardCreatedEvent event = new CardCreatedEvent();
        BeanUtils.copyProperties(createCommand, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CardCreatedEvent event) {
        this.mobileNumber = event.getMobileNumber();
    }

    @CommandHandler
    public void on(UpdateCardCommand updateCommand) {
        CardUpdatedEvent event = new CardUpdatedEvent();
        BeanUtils.copyProperties(updateCommand, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CardUpdatedEvent event) {
        this.cardNumber = event.getCardNumber();
        this.mobileNumber = event.getMobileNumber();
        this.cardType = event.getCardType();
        this.totalLimit = event.getTotalLimit();
        this.amountUsed = event.getAmountUsed();
        this.availableAmount = event.getAvailableAmount();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void on(DeleteCardCommand deleteCommand) {
        CardDeletedEvent event = new CardDeletedEvent();
        BeanUtils.copyProperties(deleteCommand, event);
        AggregateLifecycle.apply(event);
    }


}
