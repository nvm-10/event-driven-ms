package com.nvm10.profile.service.impl;


import com.nvm10.common.event.AccountDataChangeEvent;
import com.nvm10.common.event.CardDataChangeEvent;
import com.nvm10.common.event.CustomerDataChangeEvent;
import com.nvm10.common.event.LoanDataChangeEvent;
import com.nvm10.profile.dto.ProfileDto;
import com.nvm10.profile.entity.Profile;
import com.nvm10.profile.exception.ResourceNotFoundException;
import com.nvm10.profile.mapper.ProfileMapper;
import com.nvm10.profile.repository.ProfileRepository;
import com.nvm10.profile.service.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private ProfileRepository profileRepository;

    @Override
    public ProfileDto fetchProfile(String mobileNumber) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        ProfileDto customerDto = ProfileMapper.mapToProfileDto(profile, new ProfileDto());
        return customerDto;
    }

    @Override
    public void handleCustomerDataChangeEvent(CustomerDataChangeEvent event) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(event.getMobileNumber(), true)
                .orElseGet(Profile::new);
        profile.setMobileNumber(event.getMobileNumber());
        if (profile.getName() == null) {
            profile.setName(event.getName());
        }
        profile.setActiveSw(event.isActiveSw());
        profileRepository.save(profile);
    }

    @Override
    public void handleAcocuntDataChangeEvent(AccountDataChangeEvent event) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(event.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", event.getMobileNumber()));

        profile.setAccountNumber(event.getAccountNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleCardDataChangeEvent(CardDataChangeEvent event) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(event.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", event.getMobileNumber()));
        profile.setCardNumber(event.getCardNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleLoanDataChangeEvent(LoanDataChangeEvent event) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(event.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", event.getMobileNumber()));
        profile.setLoanNumber(event.getLoanNumber());
        profileRepository.save(profile);
    }


}
