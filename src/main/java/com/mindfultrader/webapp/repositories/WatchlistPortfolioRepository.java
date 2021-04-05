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
	public List<WatchlistPortfolio> findByUserIDAndType(long userid, String type);

}
