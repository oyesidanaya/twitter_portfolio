package org.yesid.test.portfoliojavaapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yesid.test.portfoliojavaapp.dto.request.PortfolioRequest;
import org.yesid.test.portfoliojavaapp.models.Portfolio;
import org.yesid.test.portfoliojavaapp.services.PortfolioService;

@AllArgsConstructor
@RestController
public class PortfolioRestController {

    private final PortfolioService portfolioService;

    @GetMapping("portfolio/{id}")
    public Portfolio findById(@PathVariable Integer id) {
        return portfolioService.findById(id);
    }

    @PutMapping("portfolio/{id}")
    public Portfolio updatePortfolio(@PathVariable Integer id,
                                     @RequestBody PortfolioRequest portfolioRequest){
        Portfolio savedPortfolio = portfolioService.findById(id);

        return portfolioService.updatePortfolio(savedPortfolio, portfolioRequest);
    }
}
