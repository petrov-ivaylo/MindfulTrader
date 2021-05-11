package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with the ConfirmationToken entity
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
	
	// Want to look up entries by confirmation token
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}