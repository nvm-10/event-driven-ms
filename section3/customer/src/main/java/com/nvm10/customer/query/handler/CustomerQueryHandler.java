package com.nvm10.customer.query.handler;

import com.nvm10.customer.dto.CustomerDto;
import com.nvm10.customer.query.FindCustomerQuery;
import com.nvm10.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final ICustomerService customerService;

    @QueryHandler
    public CustomerDto fetchCustomer(FindCustomerQuery query) {
        return customerService.fetchCustomer(query.getMobileNumber());
    }
}
