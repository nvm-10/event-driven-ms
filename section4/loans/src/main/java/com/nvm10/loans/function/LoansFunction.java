package com.nvm10.loans.function;


import com.nvm10.common.dto.MobileNumberUpdateDto;
import com.nvm10.loans.service.ILoansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class LoansFunction {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateLoansMobileNumber(ILoansService loansService) {
        return mobileNumberUpdateDto -> {
            log.info("Updating mobile number for loans {}", mobileNumberUpdateDto);
            loansService.updateMobileNumber(mobileNumberUpdateDto);
            log.info("Updated mobile number for loans {}", mobileNumberUpdateDto);
        };
    }
}
