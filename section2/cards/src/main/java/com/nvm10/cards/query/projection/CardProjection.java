package com.nvm10.cards.query.projection;

import com.nvm10.cards.command.event.CardCreatedEvent;
import com.nvm10.cards.command.event.CardDeletedEvent;
import com.nvm10.cards.command.event.CardUpdatedEvent;
import com.nvm10.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardProjection {

    private final ICardsService cardsService;

    @EventHandler
    public void on(CardCreatedEvent event) {
       cardsService.createCard(event.getMobileNumber());
    }

    @EventHandler
    public void on(CardUpdatedEvent event) {
        cardsService.updateCard(event);
    }

    @EventHandler
    public void on(CardDeletedEvent event) {
        cardsService.deleteCard(event.getCardNumber());
    }
}
