package com.mb.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.user.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
