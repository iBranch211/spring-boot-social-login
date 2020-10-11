package com.spring.social.service;

import com.spring.social.exception.role.RoleNotFoundException;
import com.spring.social.exception.user.EmailAlreadyExistsException;
import com.spring.social.exception.user.UserNotFoundException;
import com.spring.social.exception.user.UsernameAlreadyExistsException;
import com.spring.social.model.Role;
import com.spring.social.model.User;
import com.spring.social.model.enumerated.Provider;
import com.spring.social.model.enumerated.RoleName;
import com.spring.social.payload.request.LoginRequest;
import com.spring.social.payload.request.UserCreateRequest;
import com.spring.social.payload.response.JwtResponse;
import com.spring.social.repository.RoleRepository;
import com.spring.social.repository.UserRepository;
import com.spring.social.security.UserDetailsImpl;
import com.spring.social.security.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    AuthenticationManager authenticationManager;
    JwtTokenUtil jwtTokenUtil;
    UserRepository userRepository;
    RoleRepository roleRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;


    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtTokenUtil.generateJwtToken(userPrincipal);

        return new JwtResponse(jwt);
    }

    @Override
    public User getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user=modelMapper.map(userCreateRequest, User.class);
        if(user.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException());

        user.setRole(role);
        user.setProvider(Provider.PROVIDER_LOCAL);

        user=userRepository.save(user);
        return user;
    }
}
