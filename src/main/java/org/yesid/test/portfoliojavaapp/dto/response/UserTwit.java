package org.yesid.test.portfoliojavaapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserTwit {
    private long id;
    private Date createdAt;
    private String text;
}
