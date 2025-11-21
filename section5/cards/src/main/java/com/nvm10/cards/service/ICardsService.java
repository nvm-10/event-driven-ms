package com.nvm10.cards.service;

import com.nvm10.cards.command.event.CardUpdatedEvent;
import com.nvm10.cards.dto.CardsDto;

public interface ICardsService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     *
     * @param cardUpdatedEvent - CardUpdatedEvent Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardUpdatedEvent cardUpdatedEvent);

    /**
     *
     * @param cardNumber - Input Card Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean deleteCard(Long cardNumber);

    /**
     *
     * @param currentMobileNumber - Current Mobile Number
     * @param newMobileNumber - New Mobile Number
     * @return boolean indicating if the update of Mobile Number is successful or not
     */
    boolean updateCardMobileNumber(String currentMobileNumber, String newMobileNumber);
}
