package org.yesid.test.portfoliojavaapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yesid.test.portfoliojavaapp.dto.request.PortfolioRequest;
import org.yesid.test.portfoliojavaapp.dto.response.PortfolioUser;
import org.yesid.test.portfoliojavaapp.dto.response.UserTwit;
import org.yesid.test.portfoliojavaapp.exceptions.PortfolioNotFoundException;
import org.yesid.test.portfoliojavaapp.models.Portfolio;
import org.yesid.test.portfoliojavaapp.persistence.PortfolioRepository;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService{

    private final PortfolioRepository portfolioRepository;
    private final TwitterApiService twitterApiService;

    @Override
    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio findById(Integer id) {
        return portfolioRepository.findById(id).orElseThrow(PortfolioNotFoundException::new);
    }

    @Override
    public PortfolioUser getPortfolioUserById(Integer id) {
        Portfolio portfolio = findById(id);
        List<Status> timelineStatuses = null;
        try {
            timelineStatuses = twitterApiService.getUserTimeline(portfolio.getTwitterUserName());
        } catch (TwitterException e) {
            System.out.println("No tweets found for user: " + e.getStackTrace());
        }

        return getPortfolioUserInfo(portfolio, timelineStatuses);
    }

    protected PortfolioUser getPortfolioUserInfo(Portfolio portfolio, List<Status> timelineStatuses) {
        PortfolioUser portfolioUser = PortfolioUser.builder()
                .id(portfolio.getId())
                .description(portfolio.getDescription())
                .imageUrl(portfolio.getImageUrl())
                .title(portfolio.getTitle())
                .twitterUserName(portfolio.getTwitterUserName())
                .build();
        if(!CollectionUtils.isEmpty(timelineStatuses)) {
            portfolioUser.setUserTwits(timelineStatuses.stream()
                    .map(this::getUserTwit)
                    .collect(Collectors.toList())
            );
        }
        return portfolioUser;
    }

    protected UserTwit getUserTwit(Status status) {
        return UserTwit.builder()
                .id(status.getId())
                .createdAt(status.getCreatedAt())
                .text(status.getText())
                .build();
    }

    @Override
    public Portfolio updatePortfolio(Portfolio portfolio, PortfolioRequest portfolioRequest) {
        if(!Objects.equals(portfolio.getDescription(), portfolioRequest.getDescription())) {
            portfolio.setDescription(portfolioRequest.getDescription());
        }
        if(!Objects.equals(portfolio.getImageUrl(), portfolioRequest.getImageUrl())) {
            portfolio.setImageUrl(portfolioRequest.getImageUrl());
        }
        if(!Objects.equals(portfolio.getTitle(), portfolioRequest.getTitle())) {
            portfolio.setTitle(portfolioRequest.getTitle());
        }
        if(!Objects.equals(portfolio.getTwitterUserName(), portfolioRequest.getTwitterUserName())) {
            portfolio.setTwitterUserName(portfolioRequest.getTwitterUserName());
        }
        return portfolioRepository.save(portfolio);
    }
}
