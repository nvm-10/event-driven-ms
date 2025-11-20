package com.nvm10.customer.command.event;

import lombok.Data;

@Data
public class CustomerDeletedEvent {

    public String customerId;
    public boolean activeSw;
}
