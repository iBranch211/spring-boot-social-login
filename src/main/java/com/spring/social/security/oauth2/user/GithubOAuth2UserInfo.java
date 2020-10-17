package com.spring.social.security.oauth2.user;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getFullName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getFirstName() {
        String name = (String) attributes.get("name");
        int index = name.lastIndexOf(" ");
        if (index > -1) {
            return name.substring(0, index);
        }
        return name;
    }

    @Override
    public String getLastName() {
        String name = (String) attributes.get("name");
        int index = name.lastIndexOf(" ");
        if (index > -1) {
            return name.substring(index + 1 , name.length());
        }
        return name;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}