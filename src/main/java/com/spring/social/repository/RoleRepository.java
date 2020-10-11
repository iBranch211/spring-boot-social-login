package com.spring.social.repository;


import com.spring.social.model.Role;
import com.spring.social.model.enumerated.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}