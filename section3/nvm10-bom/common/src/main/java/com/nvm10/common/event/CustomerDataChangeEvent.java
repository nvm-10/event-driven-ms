package com.nvm10.common.event;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.axonframework.serialization.Revision;

@Data
public class CustomerDataChangeEvent {

    private String name;
    private String mobileNumber;
    private boolean activeSw;
}
