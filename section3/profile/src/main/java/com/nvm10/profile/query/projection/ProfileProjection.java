package com.nvm10.profile.query.projection;


import com.nvm10.profile.service.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileProjection {

    private final IProfileService profileService;


}
