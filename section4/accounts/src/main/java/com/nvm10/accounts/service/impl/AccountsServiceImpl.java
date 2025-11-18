package com.nvm10.accounts.service.impl;

import com.nvm10.accounts.constants.AccountsConstants;
import com.nvm10.accounts.dto.AccountsDto;
import com.nvm10.accounts.entity.Accounts;
import com.nvm10.accounts.exception.AccountAlreadyExistsException;
import com.nvm10.accounts.exception.ResourceNotFoundException;
import com.nvm10.accounts.mapper.AccountsMapper;
import com.nvm10.accounts.repository.AccountsRepository;
import com.nvm10.accounts.service.IAccountsService;
import com.nvm10.common.dto.MobileNumberUpdateDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class AccountsServiceImpl  implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final StreamBridge streamBridge;

    /**
     * @param mobileNumber - String
     */
    @Override
    public void createAccount(String mobileNumber) {
        Optional<Accounts> optionalAccounts= accountsRepository.findByMobileNumberAndActiveSw(mobileNumber,
                AccountsConstants.ACTIVE_SW);
        if(optionalAccounts.isPresent()){
            throw new AccountAlreadyExistsException("Account already registered with given mobileNumber "+mobileNumber);
        }
        accountsRepository.save(createNewAccount(mobileNumber));
    }

    /**
     * @param mobileNumber - String
     * @return the new account details
     */
    private Accounts createNewAccount(String mobileNumber) {
        Accounts newAccount = new Accounts();
        newAccount.setMobileNumber(mobileNumber);
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setActiveSw(AccountsConstants.ACTIVE_SW);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public AccountsDto fetchAccount(String mobileNumber) {
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(mobileNumber, AccountsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
        );
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());
        return accountsDto;
    }

    /**
     * @param accountsDto - AccountsDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(AccountsDto accountsDto) {
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(accountsDto.getMobileNumber(),
                AccountsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber",
                accountsDto.getMobileNumber()));
        AccountsMapper.mapToAccounts(accountsDto, account);
        accountsRepository.save(account);
        return  true;
    }

    /**
     * @param accountNumber - Input Account Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(Long accountNumber) {
        Accounts account = accountsRepository.findById(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString())
        );
        account.setActiveSw(AccountsConstants.IN_ACTIVE_SW);
        accountsRepository.save(account);
        return true;
    }

    @Override
    public boolean updateMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        String currentMobileNumber = mobileNumberUpdateDto.getCurrentMobileNumber();
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(currentMobileNumber, AccountsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", currentMobileNumber));
        account.setMobileNumber(mobileNumberUpdateDto.getNewMobileNumber());
        accountsRepository.save(account);
        updateCardMobileNumber(mobileNumberUpdateDto);
        return true;
    }

    private void updateCardMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto) {
        log.info("Sending mobileNumberUpdateDto event to card service  {}",
                mobileNumberUpdateDto);
        streamBridge.send("updateCardMobileNumber-out-0", mobileNumberUpdateDto);
        log.info("Sent mobileNumberUpdateDto event to card service");
    }


}
