package com.nvm10.gatewayserver.handler;

import com.nvm10.gatewayserver.client.CustomerSummaryClient;
import com.nvm10.gatewayserver.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerCompositeHandler {

    private final CustomerSummaryClient customerSummaryClient;

    public Mono<ServerResponse> getCustomerDetails(ServerRequest serverRequest) {
        String mobileNumber = serverRequest.queryParam("mobileNumber").get();

        Mono<ResponseEntity<CustomerDto>> customerDetails = customerSummaryClient.fetchCustomerDetails(mobileNumber);
        Mono<ResponseEntity<AccountsDto>> accountDetails = customerSummaryClient.fetchAccountDetails(mobileNumber);
        Mono<ResponseEntity<CardsDto>> cardDetails = customerSummaryClient.fetchCardDetails(mobileNumber)
                .onErrorResume(ex -> Mono.just(ResponseEntity.ok().body(null)));
        Mono<ResponseEntity<LoansDto>> loansDetails = customerSummaryClient.fetchLoanDetails(mobileNumber)
                .onErrorResume(ex -> Mono.just(ResponseEntity.ok().body(null)));

        return Mono.zip(customerDetails, accountDetails, cardDetails, loansDetails)
                .flatMap( tuple -> {
                    CustomerDto customerDto = tuple.getT1().getBody();
                    AccountsDto accountsDto = tuple.getT2().getBody();
                    CardsDto cardsDto = tuple.getT3().getBody();
                    LoansDto loansDto = tuple.getT4().getBody();
                    CustomerSummaryDto customerSummaryDto = new CustomerSummaryDto(customerDto, accountsDto, cardsDto, loansDto);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(customerSummaryDto));
                });
    }

}
