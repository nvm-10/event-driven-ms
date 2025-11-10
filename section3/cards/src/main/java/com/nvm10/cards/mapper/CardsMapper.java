package com.nvm10.cards.mapper;

import com.nvm10.cards.command.UpdateCardCommand;
import com.nvm10.cards.command.event.CardUpdatedEvent;
import com.nvm10.cards.dto.CardsDto;
import com.nvm10.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto) {
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setActiveSw(cards.isActiveSw());
        return cardsDto;
    }

    public static Cards mapToCards(CardsDto cardsDto, Cards cards) {
        cards.setCardType(cardsDto.getCardType());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        return cards;
    }

    public static Cards mapEventToCards(CardUpdatedEvent event, Cards cards) {
        cards.setCardType(event.getCardType());
        cards.setTotalLimit(event.getTotalLimit());
        cards.setAvailableAmount(event.getAvailableAmount());
        cards.setAmountUsed(event.getAmountUsed());
        return cards;
    }

}
