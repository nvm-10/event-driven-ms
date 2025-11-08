package com.nvm10.loans.command.event;

import lombok.Data;

@Data
public class LoanDeletedEvent {

    private String loanNumber;
    private boolean activeSw;
}
