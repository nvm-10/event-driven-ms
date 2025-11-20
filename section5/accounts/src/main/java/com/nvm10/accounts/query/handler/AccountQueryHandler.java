package com.nvm10.accounts.query.handler;

import com.nvm10.accounts.dto.AccountsDto;
import com.nvm10.accounts.query.FindAccountQuery;
import com.nvm10.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountQueryHandler {

    private final IAccountsService accountsService;

    @QueryHandler
    public AccountsDto getAccounts(FindAccountQuery query) {
        return accountsService.fetchAccount(query.getMobileNumber());
    }
}
