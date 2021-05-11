package com.mindfultrader.webapp.models;

/*
 * JPA representation of watchlistportfolio table 
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
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