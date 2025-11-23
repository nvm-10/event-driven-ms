package com.nvm10.customer.service;

import com.nvm10.common.event.CustomerMobileNumberUpdatedEvent;
import com.nvm10.customer.command.event.CustomerUpdatedEvent;
import com.nvm10.customer.dto.CustomerDto;
import com.nvm10.customer.entity.Customer;

public interface ICustomerService {

    /**
     * @param customer - Customer Object
     */
    void createCustomer(Customer customer);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    CustomerDto fetchCustomer(String mobileNumber);

    /**
     * @param customerUpdatedEvent - CustomerUpdatedEvent Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent);

    /**
     * @param customerId - Input Customer ID
     * @return boolean indicating if the delete of Customer details is successful or not
     */
    boolean deleteCustomer(String customerId);

    /**
     * @param currentMobileNumber - Current Mobile Number
     * @param newMobileNumber     - New Mobile Number
     * @return boolean indicating if the update of Customer Mobile Number is successful or not
     */
    boolean updateCustomerMobileNumber(String currentMobileNumber, String newMobileNumber);

    /**
     * @param currenMobileNumber - String Object
     * @param newMobileNumber    - String Object
     * @return boolean indicating if the update of Customer Mobile Number is successful or not
     */
    boolean updateCustomerMobileNumberRollback(String currenMobileNumber, String newMobileNumber);
}
