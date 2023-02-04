package com.mb.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.common.enums.UserType;
import com.mb.user.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role> findByUserType(UserType userType);

}
