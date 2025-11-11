package com.nvm10.profile.service;

import com.nvm10.common.event.AccountDataChangeEvent;
import com.nvm10.common.event.CardDataChangeEvent;
import com.nvm10.common.event.CustomerDataChangeEvent;
import com.nvm10.common.event.LoanDataChangeEvent;
import com.nvm10.profile.dto.ProfileDto;

public interface IProfileService {

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    ProfileDto fetchProfile(String mobileNumber);

    /**
     * Handle customer data change event
     *
     * @param event - CustomerDataChangeEvent Identifier
     */
    void handleCustomerDataChangeEvent(CustomerDataChangeEvent event);

    /**
     * Handle account data change event
     *
     * @param event - AccountDataChangeEvent Identifier
     */
    void handleAcocuntDataChangeEvent(AccountDataChangeEvent event);

    /**
     * Handle card data change event
     *
     * @param event - CardDataChangeEvent Identifier
     */
    void handleCardDataChangeEvent(CardDataChangeEvent event);

    /**
     * Handle loan data change event
     *
     * @param event - LoanDataChangeEvent Identifier
     */
    void handleLoanDataChangeEvent(LoanDataChangeEvent event);

}
