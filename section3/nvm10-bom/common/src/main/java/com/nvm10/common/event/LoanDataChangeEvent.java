package com.nvm10.common.event;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
public class LoanDataChangeEvent {
    private String mobileNumber;
    private Long loanNumber;
}
