package com.mindfultrader.webapp.models;

/*
 * ORM (Spring JPA) representation of watchlistportfolio table 
 * 
 *  * Notes:
 *  - Name parameter inside @Column annotation must match DB attribute name
 *  - The name specified after Private <type> <name> is the field name we work with in the java code - ie can be set arbitrarily as suits our purpose
 *  - for id, email and password the name of the java object field matches the name of the db relation attribute, and so spring JPA figures out the db name 
 *     from that. 	
 *  - INTs in database are represented in Java as Longs, VARCHAR as strings
 * 
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "watchlistportfolio")
public class WatchlistPortfolio {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long List_ID;
     
    @Column(name = "User_ID", nullable = false, length = 64)
    private Long userid;
     
    @Column(name = "Type", nullable = false, length = 1)
    private String type;
    
    @Column(name = "Company_ID", nullable = false, length = 11)
    private Long companyid;

	public Long getList_ID() {
		return List_ID;
	}

	public void setList_ID(Long list_ID) {
		List_ID = list_ID;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
}