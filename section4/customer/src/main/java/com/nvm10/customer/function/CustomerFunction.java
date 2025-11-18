package com.nvm10.customer.function;

import com.nvm10.common.dto.MobileNumberUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CustomerFunction {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateMobileNumberStatus() {

    return mobileNumberUpdateDto -> {
            log.info("Received mobile number update event from loans servive: {}", mobileNumberUpdateDto);
        };
    }
}
