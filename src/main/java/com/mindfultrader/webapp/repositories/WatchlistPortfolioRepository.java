package com.mindfultrader.webapp.repositories;

/*
 * Repository to work with watchlistPortfolio entity (WatchlistPortfolio table)
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

	//temporary query - to be deleted when a company cannot be added to both watchlist and portfolio
	public WatchlistPortfolio findByUseridAndCompanyidAndType(long userid, long companyid, String type);
}
