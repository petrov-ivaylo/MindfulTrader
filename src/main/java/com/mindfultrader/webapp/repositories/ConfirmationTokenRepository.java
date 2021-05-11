package com.mindfultrader.webapp.repositories;

//Repository to work with the ConfirmationToken entity
import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    
    //ConfirmationToken findById(Long id);
}