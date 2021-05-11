package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with user entity (customers table in db)
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindfultrader.webapp.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	// Custom query so we can look up users by email (NOTE: ?1 is a postgreSQL expression, and 
	// I am fairly sure it means to only pull obj if the email exists. CAN WE CHANGE THIS TO MYSQL?!)
	@Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);
	
	public boolean existsUserByEmail(String email);
}