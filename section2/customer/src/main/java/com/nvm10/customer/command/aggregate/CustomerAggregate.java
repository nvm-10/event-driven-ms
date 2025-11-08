package com.nvm10.customer.command.aggregate;

import com.nvm10.customer.command.CreateCustomerCommand;
import com.nvm10.customer.command.DeleteCustomerCommand;
import com.nvm10.customer.command.UpdateCustomerCommand;
import com.nvm10.customer.command.event.CustomerCreatedEvent;
import com.nvm10.customer.command.event.CustomerDeletedEvent;
import com.nvm10.customer.command.event.CustomerUpdatedEvent;
import com.nvm10.customer.exception.ResourceNotFoundException;
import com.nvm10.customer.repository.CustomerRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    public String customerId;
    public String name;
    public String email;
    public String mobileNumber;
    public boolean activeSw;

    public CustomerAggregate() {}

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command, CustomerRepository customerRepository) {
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

    @CommandHandler
    public void on(UpdateCustomerCommand command, EventStore eventStore) {
        List<?> commands = eventStore.readEvents(command.getCustomerId()).asStream().toList();
        if(commands.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "customerId", command.getCustomerId());
        }
        CustomerUpdatedEvent event = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent) {
        this.name = customerUpdatedEvent.getName();
        this.email = customerUpdatedEvent.getEmail();
        this.mobileNumber = customerUpdatedEvent.getMobileNumber();
    }

    @CommandHandler
    public void on(DeleteCustomerCommand command) {
        CustomerDeletedEvent event = new CustomerDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent customerDeletedEvent) {
        this.activeSw = customerDeletedEvent.isActiveSw();
    }
}
