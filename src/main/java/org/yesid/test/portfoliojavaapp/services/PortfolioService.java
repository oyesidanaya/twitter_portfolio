package org.yesid.test.portfoliojavaapp.services;

import org.yesid.test.portfoliojavaapp.dto.request.PortfolioRequest;
import org.yesid.test.portfoliojavaapp.dto.response.PortfolioUser;
import org.yesid.test.portfoliojavaapp.models.Portfolio;

import java.util.List;

public interface PortfolioService {
    List<Portfolio> findAll();

    Portfolio findById(Integer id);

    PortfolioUser getPortfolioUserById(Integer id);

    Portfolio updatePortfolio(Portfolio portfolio, PortfolioRequest portfolioRequest);
}
