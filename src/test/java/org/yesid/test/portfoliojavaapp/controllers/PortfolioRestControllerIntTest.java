package org.yesid.test.portfoliojavaapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yesid.test.portfoliojavaapp.IntegrationTestBase;
import org.yesid.test.portfoliojavaapp.exceptions.PortfolioNotFoundException;
import org.yesid.test.portfoliojavaapp.models.Portfolio;
import org.yesid.test.portfoliojavaapp.persistence.PortfolioRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PortfolioRestControllerIntTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Test
    public void getPortfolio_Success() throws Exception {
        Portfolio portfolio = portfolioRepository.findById(1).orElse(null);
        mvc.perform(get("/portfolio/1").accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("id", is(1)),
                        jsonPath("description", is(portfolio.getDescription())),
                        jsonPath("imageUrl", is(portfolio.getImageUrl())),
                        jsonPath("title", is(portfolio.getTitle())),
                        jsonPath("twitterUserName", is(portfolio.getTwitterUserName()))
                );
    }

    @Test
    public void getPortfolio_NotFound() throws Exception {
        Portfolio portfolio = portfolioRepository.findById(2).orElse(null);
        assertNull(portfolio);
        mvc.perform(get("/portfolio/2").accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        result -> assertTrue(result.getResolvedException() instanceof PortfolioNotFoundException),
                        result -> assertEquals("Portfolio Not Found", result.getResolvedException().getMessage())
                );
    }

    @Test
    public void updatePortfolio_Success() throws Exception {
        mvc.perform(put("/portfolio/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getPortfolioToUpdate()))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("id", is(1)),
                        jsonPath("description", is("updated_description")),
                        jsonPath("imageUrl", is("updated_image")),
                        jsonPath("title", is("updated_title")),
                        jsonPath("twitterUserName", is("updated_twitter_username"))
                );
    }

    private String getPortfolioToUpdate() {
        return "{" +
                "  \"description\": \"updated_description\"," +
                "  \"imageUrl\": \"updated_image\"," +
                "  \"title\": \"updated_title\"," +
                "  \"twitterUserName\": \"updated_twitter_username\"" +
                "}";
    }
}
