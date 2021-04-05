package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with watchlistPortfolio entity (WatchlistPortfolio table)
 * 
 * created: 5th April 2021
 * updated: 5th April 2021
 * 
 * Author: Emma
 * 
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfultrader.webapp.models.WatchlistPortfolio;

public interface WatchlistPortfolioRepository extends JpaRepository<WatchlistPortfolio, Long>{
	
	// Want to look up entries for specific user by 'userid' of either portfolio or watchlist
	// as defined in 'type'
	public List<WatchlistPortfolio> findByUseridAndType(long userid, String type);
	
	// Want to look up entries by company id and user id
	public List<WatchlistPortfolio> findByUseridAndCompanyid(long userid, long companyid);
	
	// Query to check if user has already added company to either watchlist or portfolio
	// Source: https://www.baeldung.com/spring-data-exists-query
	public boolean existsWatchlistPortfolioByUseridAndCompanyid(long userid, long companyid);

	//technically not necessary as a company cannot be on both portfolio and watchlist for the same user, but keeping in case the is an error and it happens anyways
	public WatchlistPortfolio findByUseridAndCompanyidAndType(long userid, long companyid, String type);
	
}
