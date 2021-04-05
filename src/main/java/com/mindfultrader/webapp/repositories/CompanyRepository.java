package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with company entity, ie access companies table in database
 * 
 * Created: 5th April 2021
 * Updated:
 * Author: Emma
 * 
 */


import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	
}
