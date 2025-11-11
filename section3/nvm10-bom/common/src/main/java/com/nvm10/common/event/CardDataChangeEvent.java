package com.nvm10.common.event;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
public class CardDataChangeEvent {

    private String mobileNumber;
    private Long cardNumber;
}
