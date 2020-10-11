package com.spring.social.bootstrap;


import com.spring.social.exception.role.RoleNotFoundException;
import com.spring.social.model.Role;
import com.spring.social.model.User;
import com.spring.social.model.enumerated.Provider;
import com.spring.social.model.enumerated.RoleName;
import com.spring.social.repository.RoleRepository;
import com.spring.social.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class InitDataLoader implements CommandLineRunner {

   final RoleRepository roleRepository;

   final UserRepository userRepository;

   final PasswordEncoder passwordEncoder;


    @Value("${admin.username}")
    String adminUsername;

    @Value("${admin.password}")
    String adminPassword;


    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findAll().isEmpty()){
            initRole();
        }

        if(userRepository.findAll().isEmpty()){
            initAdminUser();

        }

    }

    private void initRole(){
        Role roleAdmin=new Role(RoleName.ROLE_ADMIN);
        Role roleUser=new Role(RoleName.ROLE_USER);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

    }

    private void initAdminUser(){

        User user = new User();
        user.setUsername(adminUsername);
        user.setEmail("meet.admin@gmail.com");
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setProvider(Provider.PROVIDER_LOCAL);

        Role role = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RoleNotFoundException());

        user.setRole(role);

        userRepository.save(user);
    }
}
