package com.nvm10.profile.query.handler;

import com.nvm10.profile.dto.ProfileDto;
import com.nvm10.profile.query.FindProfileQuery;
import com.nvm10.profile.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileQueryHandler {

    private final IProfileService profileService;

    @QueryHandler
    public ProfileDto fetchProfile(FindProfileQuery query) {
        return profileService.fetchProfile(query.getMobileNumber());
    }
}
