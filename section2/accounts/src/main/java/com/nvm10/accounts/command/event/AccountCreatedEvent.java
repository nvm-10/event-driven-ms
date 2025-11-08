package com.nvm10.accounts.command.event;

import lombok.Data;

@Data
public class AccountCreatedEvent {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private String mobileNumber;
    private boolean activeSw;
}
