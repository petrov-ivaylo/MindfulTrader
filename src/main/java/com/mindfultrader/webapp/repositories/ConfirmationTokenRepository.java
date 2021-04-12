package com.mindfultrader.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}