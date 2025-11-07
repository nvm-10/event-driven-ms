package com.nvm10.customer.command.event;

import lombok.Data;

@Data
public class CustomerCreatedEvent {

    public String customerId;
    public String name;
    public String email;
    public String mobileNumber;
    public boolean activeSw;
}
