package com.nvm10.profile.service.impl;


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


}
