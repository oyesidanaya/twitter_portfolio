package org.yesid.test.portfoliojavaapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.yesid.test.portfoliojavaapp.dto.response.PortfolioUser;
import org.yesid.test.portfoliojavaapp.models.Portfolio;
import org.yesid.test.portfoliojavaapp.services.PortfolioService;

import java.util.List;

@AllArgsConstructor
@Controller
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/")
    public String getPortfolios(Model model) {

        List<Portfolio> portfolios = portfolioService.findAll();

        model.addAttribute("portfolios", portfolios);

        return "portfolio-list";
    }

    @GetMapping("portfolio/detail/{id}")
    public String getPortfolio(@PathVariable Integer id, Model model) {
        PortfolioUser portfolioUser = portfolioService.getPortfolioUserById(id);
        model.addAttribute("portfolio", portfolioUser);
        return "portfolio-detail";
    }
}
