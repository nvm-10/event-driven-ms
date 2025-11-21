package com.nvm10.accounts.query.projection;

import com.nvm10.accounts.command.event.AccountCreatedEvent;
import com.nvm10.accounts.command.event.AccountUpdatedEvent;
import com.nvm10.accounts.service.IAccountsService;
import com.nvm10.common.event.AccountMobileNumberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountProjection {

    private final IAccountsService accountsService;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        accountsService.createAccount(event.getMobileNumber());
    }

    @EventHandler
    public void on(AccountUpdatedEvent event) {
        accountsService.updateAccount(event);
    }

    @EventHandler
    public void on(AccountMobileNumberUpdatedEvent event) {
        accountsService.updateAccountMobileNumber(event.getCurrentMobileNumber(), event.getNewMobileNumber());
    }
}
