package com.nvm10.accounts.query.projection;

import com.nvm10.accounts.command.event.AccountCreatedEvent;
import com.nvm10.accounts.command.event.AccountUpdatedEvent;
import com.nvm10.accounts.entity.Accounts;
import com.nvm10.accounts.service.IAccountsService;
import com.nvm10.common.event.AccountMobileNumberRollbackedEvent;
import com.nvm10.common.event.AccountMobileNumberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountProjection {

    private final IAccountsService accountsService;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        Accounts account = new Accounts();
        BeanUtils.copyProperties(event, account);
        accountsService.createAccount(account);
    }

    @EventHandler
    public void on(AccountUpdatedEvent event) {
        accountsService.updateAccount(event);
    }

    @EventHandler
    public void on(AccountMobileNumberUpdatedEvent event) {
        accountsService.updateAccountMobileNumber(event.getCurrentMobileNumber(), event.getNewMobileNumber());
    }

    @EventHandler
    public void on(AccountMobileNumberRollbackedEvent event) {
        accountsService.updateAccountMobileNumber(event.getNewMobileNumber(), event.getCurrentMobileNumber());
    }
}
