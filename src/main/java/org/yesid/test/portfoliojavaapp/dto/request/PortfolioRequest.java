package org.yesid.test.portfoliojavaapp.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioRequest {
    private String description;
    private String imageUrl;
    private String title;
    private String twitterUserName;
}
