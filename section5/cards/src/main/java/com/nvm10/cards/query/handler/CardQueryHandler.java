package com.nvm10.cards.query.handler;

import com.nvm10.cards.dto.CardsDto;
import com.nvm10.cards.query.FindCardQuery;
import com.nvm10.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardQueryHandler {

    private final ICardsService cardsService;

    @QueryHandler
    public CardsDto fetchCard(FindCardQuery query) {
        return cardsService.fetchCard(query.getMobileNumber());
    }
}
