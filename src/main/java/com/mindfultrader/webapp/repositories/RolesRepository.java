package com.mindfultrader.webapp.repositories;

//Repository to work with the Roles entity
import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long>{

	
	
	public Roles findByname(String name);

}
