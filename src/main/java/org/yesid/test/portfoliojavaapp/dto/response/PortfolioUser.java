package org.yesid.test.portfoliojavaapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PortfolioUser {
    private Integer id;
    private String description;
    private String imageUrl;
    private String title;
    private String twitterUserName;
    private List<UserTwit> userTwits;
}
