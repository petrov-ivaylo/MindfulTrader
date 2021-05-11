package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with company entity, ie access companies table in database
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	// Want to look up entries by company name
	public Company findByCompanyName(String name);
	
}
