package com.mb.user.dao;

import com.mb.common.enums.UserType;
import com.mb.user.entity.Role;
import com.mb.user.entity.User;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public interface UserDao {

	/**
	 * User by email
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param email
	 * @return {@link User}
	 */
	User findByEmail(String email);

	/**
	 * Save user
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param user
	 * @return {@link User}
	 */
	User saveUser(User user);

	/**
	 * Role by user type
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userType
	 * @return {@link Role}
	 */
	Role roleByUserType(UserType userType);
}
