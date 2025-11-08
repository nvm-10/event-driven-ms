package com.nvm10.customer.query.projection;

import com.nvm10.customer.command.event.CustomerCreatedEvent;
import com.nvm10.customer.command.event.CustomerUpdatedEvent;
import com.nvm10.customer.entity.Customer;
import com.nvm10.customer.repository.CustomerRepository;
import com.nvm10.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ProcessingGroup("customer-group")
public class CustomerProjection {

    private final ICustomerService customerService;

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(event, customer);
        customerService.createCustomer(customer);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent event) {
        customerService.updateCustomer(event);
    }
}
