package com.nvm10.customer.service.impl;

import com.nvm10.common.event.CustomerMobileNumberUpdatedEvent;
import com.nvm10.customer.command.event.CustomerUpdatedEvent;
import com.nvm10.customer.constants.CustomerConstants;
import com.nvm10.customer.dto.CustomerDto;
import com.nvm10.customer.entity.Customer;
import com.nvm10.customer.exception.CustomerAlreadyExistsException;
import com.nvm10.customer.exception.ResourceNotFoundException;
import com.nvm10.customer.mapper.CustomerMapper;
import com.nvm10.customer.repository.CustomerRepository;
import com.nvm10.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customer.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(customerUpdatedEvent.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerUpdatedEvent.getMobileNumber()));
        CustomerMapper.mapEventToCustomer(customerUpdatedEvent, customer);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId)
        );
        customer.setActiveSw(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean updateCustomerMobileNumber(String currentMobileNumber, String newMobileNumber) {
        Customer customer =  customerRepository.findByMobileNumberAndActiveSw(currentMobileNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", currentMobileNumber));
        customer.setMobileNumber(newMobileNumber);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean updateCustomerMobileNumberRollback(String currenMobileNumber, String newMobileNumber) {
        Customer customer =  customerRepository.findByMobileNumberAndActiveSw(currenMobileNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", currenMobileNumber));
        customer.setMobileNumber(newMobileNumber);
        customerRepository.save(customer);
        return true;
    }


}
