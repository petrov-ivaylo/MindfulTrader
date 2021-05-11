package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with the Roles entity
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long>{

	
	
	public Roles findByname(String name);

}
