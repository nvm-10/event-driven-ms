package com.nvm10.common.event;

import lombok.Data;

@Data
public class CustomerMobileNumberUpdatedEvent {

    private String customerId;
    private String currentMobileNumber;
    private String newMobileNumber;
    private Long accountNumber;
    private Long cardNumber;
    private Long loanNumber;
}
