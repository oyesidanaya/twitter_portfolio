package org.yesid.test.portfoliojavaapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PortfolioNotFoundException extends RuntimeException{

    public PortfolioNotFoundException() {
        super("Portfolio Not Found");
    }

    public PortfolioNotFoundException(String message) {
        super(message);
    }
}
