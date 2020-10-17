package com.spring.social.security.oauth2.user;


import com.spring.social.exception.oauth2.OAuth2AuthenticationProcessingException;

import java.util.Map;


public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if(registrationId.equalsIgnoreCase("azure")) {
            return new AzureOAuth2UserInfo(attributes);
        }
        else if(registrationId.equalsIgnoreCase("github")) {
            return new GithubOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

}