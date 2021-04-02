package com.mindfultrader.webapp.models;

/*
 * ORM (Spring JPA) representation of watchlistportfolio table 
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
    private Long ListID;
     
    @Column(name = "Username", nullable = false, length = 64)
    private String username;
     
    @Column(name = "Type", nullable = false, length = 1)
    private String type;
    
    @Column(name = "ComapanyID", nullable = false, length = 11)
    private Integer companyid;

	public Long getListID() {
		return ListID;
	}

	public void setListID(Long listID) {
		ListID = listID;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	} 
}