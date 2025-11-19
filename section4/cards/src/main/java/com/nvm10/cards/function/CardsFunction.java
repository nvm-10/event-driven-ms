package com.nvm10.cards.function;


import com.nvm10.cards.service.ICardsService;
import com.nvm10.common.dto.MobileNumberUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CardsFunction {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateCardMobileNumber(ICardsService cardsService) {
        return mobileNumberUpdateDto -> {
            log.info("Received mobile number update event: {}", mobileNumberUpdateDto);
            cardsService.updateMobileNumber(mobileNumberUpdateDto);
            log.info("Updated mobile number update event: {}", mobileNumberUpdateDto);
        };
    }

    @Bean
    public Consumer<MobileNumberUpdateDto> rollbackCardMobileNumber(ICardsService cardsService) {
        return mobileNumberUpdateDto -> {
            log.info("Received mobile number rollback event: {}", mobileNumberUpdateDto);
            cardsService.rollbackCardMobileNumber(mobileNumberUpdateDto);
            log.info("Rolled back mobile number rollback event: {}", mobileNumberUpdateDto);
        };
    }
}
