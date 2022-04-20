package org.yesid.test.portfoliojavaapp.services;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.yesid.test.portfoliojavaapp.config.PortfolioTwitterConfig;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TwitterApiServiceTest {
    private TwitterApiService twitterApiService;
    private String username;
    private MockedConstruction<TwitterFactory> twitterFactoryMockedConstruction;

    @Mock
    private PortfolioTwitterConfig twitterConfig;
    @Mock
    private Twitter twitter;
    @Mock
    private Status twit1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        twitterApiService = new TwitterApiServiceImpl(twitterConfig);
        username = "user_name";

        twitterFactoryMockedConstruction = mockConstruction(TwitterFactory.class,
                (mock, context) -> {
                    when(mock.getInstance()).thenReturn(twitter);
                }
        );
    }

    @AfterEach
    public void teardown() {
        twitterFactoryMockedConstruction.close();
    }

    @Test
    public void getUserTimeline_Success() throws Exception {
        List<Status> userTimeline = twitterApiService.getUserTimeline(username);
        assertAll(
                () -> assertNotNull(userTimeline),
                () -> assertTrue(userTimeline.isEmpty())
        );
    }

    @Test
    public void getUserTimeline_Exception() throws Exception {
        when(twitter.getUserTimeline(anyString(), any(Paging.class))).thenThrow(new TwitterException("User not found"));
        TwitterException exception = assertThrows(TwitterException.class,
                () -> twitterApiService.getUserTimeline(username));
        assertNotNull(exception);
    }
}
