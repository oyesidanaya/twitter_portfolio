package org.yesid.test.portfoliojavaapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yesid.test.portfoliojavaapp.config.PortfolioTwitterConfig;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TwitterApiServiceImpl implements TwitterApiService {

    private final PortfolioTwitterConfig twitterConfig;

    @Override
    public List<Status> getUserTimeline(String username) throws TwitterException {
        List<Status> timeline = new ArrayList<>();
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthAccessToken(twitterConfig.getAccessToken());
        configurationBuilder.setOAuthAccessTokenSecret(twitterConfig.getAccessTokenSecret());
        configurationBuilder.setOAuthConsumerKey(twitterConfig.getApiKey());
        configurationBuilder.setOAuthConsumerSecret(twitterConfig.getApiSecretKey());

        Twitter twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
        Paging page = new Paging(1, 5);
        ResponseList<Status> userTimeline = twitter.getUserTimeline(username, page);
        if (userTimeline != null) {
            timeline.addAll(userTimeline);
        }
        return timeline;
    }
}
