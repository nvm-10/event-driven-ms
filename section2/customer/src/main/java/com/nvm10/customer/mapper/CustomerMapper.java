package com.nvm10.customer.mapper;

import com.nvm10.customer.command.event.CustomerUpdatedEvent;
import com.nvm10.customer.dto.CustomerDto;
import com.nvm10.customer.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setActiveSw(customer.isActiveSw());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        if(customerDto.isActiveSw()) {
            customer.setActiveSw(customerDto.isActiveSw());
        }
        return customer;
    }

    public static Customer mapEventToCustomer(CustomerUpdatedEvent customerUpdatedEvent, Customer customer) {
        customer.setName(customerUpdatedEvent.getName());
        customer.setEmail(customerUpdatedEvent.getEmail());
        customer.setMobileNumber(customerUpdatedEvent.getMobileNumber());

        return customer;
    }

}
