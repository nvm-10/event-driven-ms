package com.nvm10.common.event;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
public class AccountDataChangeEvent {

    private String mobileNumber;
    private Long accountNumber;
}
