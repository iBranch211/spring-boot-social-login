package com.spring.social.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.social.model.User;
import com.spring.social.model.enumerated.Provider;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserDetailsImpl implements OAuth2User, UserDetails, OidcUser {

    static final long serialVersionUID = 1L;
    String id;

    String username;

    @JsonIgnore
    String password;

    String email;

    Provider provider;

    private Collection<? extends GrantedAuthority> authorities;

    Map<String, Object> attributes;

    Map<String, Object> claims;

    OidcIdToken idToken;

    OidcUserInfo userInfo;

    public UserDetailsImpl(String id, String username, String password, String email, Provider provider, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName().name()));

        return new UserDetailsImpl(user.getId(),user.getUsername(),user.getPassword(),user.getEmail(),user.getProvider(),authorities);
    }

    public static UserDetailsImpl build(User user, Map<String, Object> attributes) {
        UserDetailsImpl userPrincipal = UserDetailsImpl.build(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public static OidcUser build(User user, Map<String, Object> attributes,OidcIdToken idToken, OidcUserInfo userInfo) {
        UserDetailsImpl userPrincipal = UserDetailsImpl.build(user);
        userPrincipal.setAttributes(attributes);
        userPrincipal.setIdToken(idToken);
        userPrincipal.setUserInfo(userInfo);
        return userPrincipal;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public Map<String, Object> getClaims() {
        return attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }
}