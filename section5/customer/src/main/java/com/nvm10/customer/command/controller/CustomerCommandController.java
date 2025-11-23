package com.nvm10.customer.command.controller;

import com.nvm10.common.command.UpdateCustomerMoblieNumberCommand;
import com.nvm10.common.dto.UpdateMobileNumberDto;
import com.nvm10.customer.command.CreateCustomerCommand;
import com.nvm10.customer.command.DeleteCustomerCommand;
import com.nvm10.customer.command.UpdateCustomerCommand;
import com.nvm10.customer.constants.CustomerConstants;
import com.nvm10.customer.dto.CustomerDto;
import com.nvm10.customer.dto.ResponseDto;
import com.nvm10.customer.query.UpdateMobileNumberSagaQuery;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class CustomerCommandController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CreateCustomerCommand createCommand = CreateCustomerCommand.builder()
                .customerId(UUID.randomUUID().toString())
                .email(customerDto.getEmail())
                .name(customerDto.getName())
                .mobileNumber(customerDto.getMobileNumber())
                .activeSw(CustomerConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(createCommand);
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.CREATED)
                .body(new ResponseDto(CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody CustomerDto customerDto) {
        UpdateCustomerCommand updateCommand = UpdateCustomerCommand.builder()
                .customerId(customerDto.getCustomerId())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .activeSw(CustomerConstants.ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(updateCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }

    @PatchMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam("customerId")
                                                      @Pattern(regexp = "(^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$)",
                                                              message = "CustomerId is invalid") String customerId) {
        DeleteCustomerCommand deleteCommand = DeleteCustomerCommand.builder()
                .customerId(customerId)
                .activeSw(CustomerConstants.IN_ACTIVE_SW)
                .build();
        commandGateway.sendAndWait(deleteCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }

    @PatchMapping("/mobile-number")
    public ResponseEntity<ResponseDto> updateMobileNumber(@Valid @RequestBody UpdateMobileNumberDto updateMobileNumberDto) {
        UpdateCustomerMoblieNumberCommand updateCommand = UpdateCustomerMoblieNumberCommand.builder()
                .customerId(updateMobileNumberDto.getCustomerId())
                .newMobileNumber(updateMobileNumberDto.getNewMobileNumber())
                .currentMobileNumber(updateMobileNumberDto.getCurrentMobileNumber())
                .accountNumber(updateMobileNumberDto.getAccountNumber())
                .cardNumber(updateMobileNumberDto.getCardNumber())
                .loanNumber(updateMobileNumberDto.getLoanNumber())
                .build();
        try (SubscriptionQueryResult<ResponseDto, ResponseDto> queryResult = queryGateway.
                subscriptionQuery(new UpdateMobileNumberSagaQuery(),
                        ResponseTypes.instanceOf(ResponseDto.class),
                        ResponseTypes.instanceOf(ResponseDto.class))) {
            commandGateway.sendAndWait(updateCommand);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(queryResult.updates().blockFirst());
        }
    }
}
