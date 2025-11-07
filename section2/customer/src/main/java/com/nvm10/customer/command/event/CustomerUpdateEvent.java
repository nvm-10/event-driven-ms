package com.nvm10.customer.command.event;

import lombok.Data;

@Data
public class CustomerUpdateEvent {

    public String customerId;
    public String name;
    public String email;
    public String mobileNumber;
    public boolean activeSw;
}
