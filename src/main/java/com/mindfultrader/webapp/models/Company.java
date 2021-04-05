package com.mindfultrader.webapp.models;


import javax.persistence.Column;

/*
 * 
 * Notes: 
 * Everything is not nullable, symbol and ID are unique
 * 
 * nullable, length and unique checked with database 05 Apr 2021
 * 
 * Created: 5 Apr 2021
 * Updated: 5 Apr 2021
 * Author: Emma
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Company_ID;
	
	@Column(name = "Company_Name", nullable = false, length = 64)
	private String companyName;
	
	@Column(name = "Company_Symbol", nullable = false, unique = true, length = 64)
	private String companySymbol;
	
	
}
