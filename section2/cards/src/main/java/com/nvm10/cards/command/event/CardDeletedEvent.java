package com.nvm10.cards.command.event;

import lombok.Data;

@Data
public class CardDeletedEvent {

    private Long cardNumber;
    private boolean activeSw;
}
