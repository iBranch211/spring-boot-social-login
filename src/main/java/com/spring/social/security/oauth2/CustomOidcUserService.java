package com.spring.social.security.oauth2;

import com.spring.social.model.User;
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
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {

    OAuthUserRegistrationService oAuthUserRegistrationService;

    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(oidcUserRequest);
        try {
            return processOidcUser(oidcUserRequest, oidcUser);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest oidcUserRequest, OidcUser oidcUser) {
        String registrationId = oidcUserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oidcUser.getAttributes());
        User user = oAuthUserRegistrationService.checkOAuth2User(oAuth2UserInfo,registrationId);
        return UserDetailsImpl.build(user, oidcUser.getAttributes(),oidcUser.getIdToken(),oidcUser.getUserInfo());
    }
}