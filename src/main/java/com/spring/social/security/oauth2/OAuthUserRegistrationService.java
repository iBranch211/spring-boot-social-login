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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Service
public class OAuthUserRegistrationService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    public User checkOAuth2User(OAuth2UserInfo oAuth2UserInfo,String registrationId){
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            Provider provider = getProvider(registrationId);
            if(!user.getProvider().equals(provider)) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(registrationId, oAuth2UserInfo);
        }
        return user;
    }

    private User registerNewUser(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setUsername(oAuth2UserInfo.getEmail());
        user.setEmail(oAuth2UserInfo.getEmail());

        Provider provider = getProvider(registrationId);
        if(provider!=null){
            user.setProvider(provider);
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException());

        user.setRole(role);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        //existingUser.setName(oAuth2UserInfo.getName());
        // existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

    private Provider getProvider(String registrationId){
        Provider provider;
        switch (registrationId) {
            case "google":
                provider = Provider.PROVIDER_GOOGLE;
                break;
            case "azure":
                provider = Provider.PROVIDER_AZURE;
                break;
            default:
                provider = null;
                break;
        }
        return provider;
    }
}
