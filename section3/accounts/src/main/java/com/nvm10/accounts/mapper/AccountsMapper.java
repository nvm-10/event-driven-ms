package com.nvm10.accounts.mapper;


import com.nvm10.accounts.command.event.AccountUpdatedEvent;
import com.nvm10.accounts.dto.AccountsDto;
import com.nvm10.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setMobileNumber(accounts.getMobileNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        accountsDto.setActiveSw(accounts.isActiveSw());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

    public static Accounts mapEventToAccounts(AccountUpdatedEvent event, Accounts accounts) {
        accounts.setAccountType(event.getAccountType());
        accounts.setBranchAddress(event.getBranchAddress());
        return accounts;
    }

}
