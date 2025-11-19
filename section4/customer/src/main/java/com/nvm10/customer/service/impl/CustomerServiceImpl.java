package com.nvm10.customer.service.impl;

import com.nvm10.common.dto.MobileNumberUpdateDto;
import com.nvm10.customer.constants.CustomerConstants;
import com.nvm10.customer.dto.CustomerDto;
import com.nvm10.customer.entity.Customer;
import com.nvm10.customer.exception.CustomerAlreadyExistsException;
import com.nvm10.customer.exception.ResourceNotFoundException;
import com.nvm10.customer.mapper.CustomerMapper;
import com.nvm10.customer.repository.CustomerRepository;
import com.nvm10.customer.service.ICustomerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    @Override
    public void createCustomer(CustomerDto customerDto) {
        customerDto.setActiveSw(CustomerConstants.ACTIVE_SW);
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customerDto.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
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
    public boolean updateCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(customerDto.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerDto.getMobileNumber()));
        CustomerMapper.mapToCustomer(customerDto, customer);
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
    @Transactional
    public boolean updateMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        String currentMobileNumber = mobileNumberUpdateDto.getCurrentMobileNumber();
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(currentMobileNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", currentMobileNumber));
        customer.setMobileNumber(mobileNumberUpdateDto.getNewMobileNumber());
        customerRepository.save(customer);
        //throw new Exception("Simulated exception to test transaction rollback");
        updateAccountMobileNumber(mobileNumberUpdateDto);
        return true;
    }

    @Override
    public boolean rollbackCustomerMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        String newMobileNumber = mobileNumberUpdateDto.getNewMobileNumber();
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(newMobileNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", newMobileNumber));
        customer.setMobileNumber(mobileNumberUpdateDto.getCurrentMobileNumber());
        customerRepository.save(customer);
        return true;
    }

    private void updateAccountMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        log.info("Sending mobileNumberUpdateDto event to account service  {}",
                mobileNumberUpdateDto);
        var result = streamBridge.send("updateAccountMobileNumber-out-0", mobileNumberUpdateDto);
        log.info("Sent mobileNumberUpdateDto event to account service with result {}",
                result);
    }

}
