package com.nvm10.profile.query.projection;


import com.nvm10.common.event.AccountDataChangeEvent;
import com.nvm10.common.event.CardDataChangeEvent;
import com.nvm10.common.event.CustomerDataChangeEvent;
import com.nvm10.common.event.LoanDataChangeEvent;
import com.nvm10.profile.service.IProfileService;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileProjection {

    private final IProfileService profileService;

    @EventHandler
    public void on(CustomerDataChangeEvent event) {
        profileService.handleCustomerDataChangeEvent(event);
    }

    @EventHandler
    public void on(AccountDataChangeEvent event) {
        profileService.handleAcocuntDataChangeEvent(event);
    }

    @EventHandler
    public void on(CardDataChangeEvent event) {
        profileService.handleCardDataChangeEvent(event);
    }

    @EventHandler
    public void on(LoanDataChangeEvent event) {
        profileService.handleLoanDataChangeEvent(event);
    }
}
