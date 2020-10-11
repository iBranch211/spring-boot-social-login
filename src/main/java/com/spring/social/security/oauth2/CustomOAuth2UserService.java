package com.spring.social.security.oauth2;

import com.spring.social.exception.oauth2.OAuth2AuthenticationProcessingException;
import com.spring.social.exception.role.RoleNotFoundException;
import com.spring.social.model.Role;
import com.spring.social.model.User;
import com.spring.social.model.enumerated.Provider;
import com.spring.social.model.enumerated.RoleName;
import com.spring.social.repository.RoleRepository;
import com.spring.social.repository.UserRepository;
import com.spring.social.security.UserDetailsImpl;
import com.spring.social.security.oauth2.user.OAuth2UserInfo;
import com.spring.social.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    OAuthUserRegistrationService oAuthUserRegistrationService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        User user = oAuthUserRegistrationService.checkOAuth2User(oAuth2UserInfo,registrationId);
        return UserDetailsImpl.build(user, oAuth2User.getAttributes());
    }



}
