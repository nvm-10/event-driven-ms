package com.nvm10.customer.command.aggregate;

import com.nvm10.customer.command.CreateCustomerCommand;
import com.nvm10.customer.command.event.CustomerCreatedEvent;
import com.nvm10.customer.constants.CustomerConstants;
import com.nvm10.customer.entity.Customer;
import com.nvm10.customer.exception.CustomerAlreadyExistsException;
import com.nvm10.customer.repository.CustomerRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

@Aggregate
public class CustomerAggregate {

    public String customerId;
    public String name;
    public String email;
    public String mobileNumber;
    public boolean activeSw;

    public CustomerAggregate() {}

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command, CustomerRepository customerRepository) {
        Optional<Customer> customer = customerRepository.findByMobileNumberAndActiveSw(command.getMobileNumber(), command.isActiveSw());
        if(customer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists for this mobile number"
                + command.getMobileNumber());
        }
        CustomerCreatedEvent event = new CustomerCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        this.customerId = event.getCustomerId();
        this.name = event.getName();
        this.email = event.getEmail();
        this.mobileNumber = event.getMobileNumber();
        this.activeSw = event.isActiveSw();
    }
}
