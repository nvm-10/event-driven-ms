package com.nvm10.gatewayserver.client;

import com.nvm10.gatewayserver.dto.AccountsDto;
import com.nvm10.gatewayserver.dto.CardsDto;
import com.nvm10.gatewayserver.dto.CustomerDto;
import com.nvm10.gatewayserver.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface CustomerSummaryClient {

    @GetExchange(value = "/nvm10/customer/api/fetch", accept = "application/json")
    Mono<ResponseEntity<CustomerDto>> fetchCustomerDetails(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value = "/nvm10/accounts/api/fetch", accept = "application/json")
    Mono<ResponseEntity<AccountsDto>> fetchAccountDetails(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value = "/nvm10/cards/api/fetch", accept = "application/json")
    Mono<ResponseEntity<CardsDto>> fetchCardDetails(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value = "/nvm10/loans/api/fetch", accept = "application/json")
    Mono<ResponseEntity<LoansDto>> fetchLoanDetails(@RequestParam("mobileNumber") String mobileNumber);

}
