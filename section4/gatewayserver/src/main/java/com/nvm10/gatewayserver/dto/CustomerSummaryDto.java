package com.nvm10.gatewayserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSummaryDto {

    private CustomerDto customer;
    private AccountsDto accounts;
    private CardsDto cards;
    private LoansDto loans;
}
