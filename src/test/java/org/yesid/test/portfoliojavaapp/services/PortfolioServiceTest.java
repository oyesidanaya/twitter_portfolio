package org.yesid.test.portfoliojavaapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.yesid.test.portfoliojavaapp.dto.request.PortfolioRequest;
import org.yesid.test.portfoliojavaapp.dto.response.PortfolioUser;
import org.yesid.test.portfoliojavaapp.exceptions.PortfolioNotFoundException;
import org.yesid.test.portfoliojavaapp.models.Portfolio;
import org.yesid.test.portfoliojavaapp.persistence.PortfolioRepository;
import twitter4j.Status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PortfolioServiceTest {
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;
    @Mock
    private TwitterApiService twitterApiService;
    @Mock
    private Status twit1;
    @Captor
    ArgumentCaptor<Portfolio> portfolioCaptor;

    private Portfolio portfolio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        portfolioService = new PortfolioServiceImpl(portfolioRepository, twitterApiService);
       portfolio = Portfolio.builder()
                .id(1)
                .description("experience_summary")
                .imageUrl("image_url")
                .title("title")
                .twitterUserName("twitter_user_name")
                .build();

        when(portfolioRepository.findById(1)).thenReturn(Optional.ofNullable(portfolio));
        when(twit1.getId()).thenReturn(1l);
        when(twit1.getCreatedAt()).thenReturn(null);
        when(twit1.getText()).thenReturn("This is my first twit");
    }

    @Test
    public void findById_Found() throws Exception {
        Portfolio foundPortfolio = portfolioService.findById(1);
        assertAll(
                () -> assertNotNull(foundPortfolio),
                () -> assertEquals(portfolio, foundPortfolio)
        );
    }

    @Test
    public void findById_NotFound() throws Exception {
        PortfolioNotFoundException exception = assertThrows(PortfolioNotFoundException.class,
                () -> portfolioService.findById(2));
        assertEquals("Portfolio Not Found", exception.getMessage());
    }

    @Test
    public void getPortfolioUserById_NoTwits() throws Exception{
        PortfolioUser portfolioUser = portfolioService.getPortfolioUserById(1);
        assertAll(
                () -> assertNotNull(portfolioUser),
                () -> assertPortfolioValues(portfolioUser),
                () -> assertNull(portfolioUser.getUserTwits())
        );
    }

    @Test
    public void getPortfolioUserById_WithTwits() throws Exception{
        List<Status> statusList = Collections.singletonList(twit1);
        when(twitterApiService.getUserTimeline(anyString())).thenReturn(statusList);
        PortfolioUser portfolioUser = portfolioService.getPortfolioUserById(1);
        assertAll(
                () -> assertNotNull(portfolioUser),
                () -> assertPortfolioValues(portfolioUser),
                () -> assertNotNull(portfolioUser.getUserTwits()),
                () -> assertTwitValues(portfolioUser)
        );
    }

    @Test
    public void updatePortfolio_WithChanges() throws Exception{
        PortfolioRequest portfolioRequest = getPortfolioRequestWithChanges();
        portfolioService.updatePortfolio(portfolio, portfolioRequest);

        verify(portfolioRepository).save(portfolioCaptor.capture());
        Portfolio updatedPortfolio = portfolioCaptor.getValue();
        assertAll(
                () -> assertNotNull(updatedPortfolio),
                () -> assertEquals(portfolioRequest.getDescription(), updatedPortfolio.getDescription()),
                () -> assertEquals(portfolioRequest.getImageUrl(), updatedPortfolio.getImageUrl()),
                () -> assertEquals(portfolioRequest.getTitle(), updatedPortfolio.getTitle()),
                () -> assertEquals(portfolioRequest.getTwitterUserName(), updatedPortfolio.getTwitterUserName())
        );
    }

    @Test
    public void updatePortfolio_WithoutChanges() throws Exception{
        PortfolioRequest portfolioRequest = getPortfolioRequestWithoutChanges();
        portfolioService.updatePortfolio(portfolio, portfolioRequest);

        verify(portfolioRepository).save(portfolioCaptor.capture());
        Portfolio updatedPortfolio = portfolioCaptor.getValue();
        assertAll(
                () -> assertNotNull(updatedPortfolio),
                () -> assertEquals(portfolioRequest.getDescription(), updatedPortfolio.getDescription()),
                () -> assertEquals(portfolioRequest.getImageUrl(), updatedPortfolio.getImageUrl()),
                () -> assertEquals(portfolioRequest.getTitle(), updatedPortfolio.getTitle()),
                () -> assertEquals(portfolioRequest.getTwitterUserName(), updatedPortfolio.getTwitterUserName())
        );
    }

    private void assertPortfolioValues(PortfolioUser portfolioUser) {
        assertAll(
                () -> assertEquals(1, portfolioUser.getId()),
                () -> assertEquals("experience_summary", portfolioUser.getDescription()),
                () -> assertEquals("image_url", portfolioUser.getImageUrl()),
                () -> assertEquals("title", portfolioUser.getTitle()),
                () -> assertEquals("twitter_user_name", portfolioUser.getTwitterUserName())
        );
    }

    private void assertTwitValues(PortfolioUser portfolioUser) {
        assertAll(
                () -> assertEquals(1, portfolioUser.getUserTwits().size()),
                () -> assertEquals(1l, portfolioUser.getUserTwits().get(0).getId()),
                () -> assertNull(portfolioUser.getUserTwits().get(0).getCreatedAt()),
                () -> assertEquals("This is my first twit", portfolioUser.getUserTwits().get(0).getText())
        );
    }

    private PortfolioRequest getPortfolioRequestWithChanges() {
        return PortfolioRequest.builder()
                .description("new_experience_summary")
                .imageUrl("new_image_url")
                .title("new_title")
                .twitterUserName("new_twitter_user_name")
                .build();
    }

    private PortfolioRequest getPortfolioRequestWithoutChanges() {
        return PortfolioRequest.builder()
                .description(portfolio.getDescription())
                .imageUrl(portfolio.getImageUrl())
                .title(portfolio.getTitle())
                .twitterUserName(portfolio.getTwitterUserName())
                .build();
    }

}
