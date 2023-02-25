package com.virtualmind.bidding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends Exception{
    public ApplicationException(String message) {
        super(message);
    }
    public ApplicationException(String message,Throwable exception) {
        super(message,exception);
    }

}
