package org.yesid.test.portfoliojavaapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yesid.test.portfoliojavaapp.models.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
}
