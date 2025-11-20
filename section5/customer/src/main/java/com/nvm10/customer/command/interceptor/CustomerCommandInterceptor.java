package com.nvm10.customer.command.interceptor;

import com.nvm10.customer.command.CreateCustomerCommand;
import com.nvm10.customer.command.DeleteCustomerCommand;
import com.nvm10.customer.command.UpdateCustomerCommand;
import com.nvm10.customer.constants.CustomerConstants;
import com.nvm10.customer.entity.Customer;
import com.nvm10.customer.exception.CustomerAlreadyExistsException;
import com.nvm10.customer.exception.ResourceNotFoundException;
import com.nvm10.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CustomerCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CustomerRepository customerRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (CreateCustomerCommand.class.equals(command.getPayload())) {
                CreateCustomerCommand commandCreate = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> customer = customerRepository.findByMobileNumberAndActiveSw(commandCreate.getMobileNumber(),
                        commandCreate.isActiveSw());
                if(customer.isPresent()) {
                    throw new CustomerAlreadyExistsException("Customer already exists for this mobile number"
                            + commandCreate.getMobileNumber());
                }
            }else if (UpdateCustomerCommand.class.equals(command.getPayload())) {
                UpdateCustomerCommand commandUpdate = (UpdateCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByMobileNumberAndActiveSw(commandUpdate.getMobileNumber(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", commandUpdate.getMobileNumber()));
            }if (DeleteCustomerCommand.class.equals(command.getPayload())) {
                DeleteCustomerCommand commandDelete = (DeleteCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByCustomerIdAndActiveSw(commandDelete.getCustomerId(),
                        CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", commandDelete.getCustomerId()));
            }
            return command;
        };
    }
}
