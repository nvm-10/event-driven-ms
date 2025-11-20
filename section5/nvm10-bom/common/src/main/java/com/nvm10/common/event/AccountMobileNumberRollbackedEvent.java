package com.nvm10.common.event;

import lombok.Data;

@Data
public class AccountMobileNumberRollbackedEvent {

    private  Long accountNumber;
    private  String customerId;
    private  String currentMobileNumber;
    private  String newMobileNumber;
    private  String errorMsg;
}
