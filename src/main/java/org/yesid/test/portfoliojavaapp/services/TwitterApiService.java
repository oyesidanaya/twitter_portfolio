package org.yesid.test.portfoliojavaapp.services;

import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

public interface TwitterApiService {
    List<Status> getUserTimeline(String username) throws TwitterException;
}
