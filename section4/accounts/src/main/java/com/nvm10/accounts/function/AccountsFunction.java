package com.nvm10.accounts.function;

import com.nvm10.accounts.service.IAccountsService;
import com.nvm10.common.dto.MobileNumberUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class AccountsFunction {


    @Bean
    public Consumer<MobileNumberUpdateDto> updateAccountMobileNumber(IAccountsService accountsService) {
        return mobileNumberUpdateDto -> {
            log.info("Updating mobile number for account {}", mobileNumberUpdateDto);
            accountsService.updateMobileNumber(mobileNumberUpdateDto);
        };
    }
}
