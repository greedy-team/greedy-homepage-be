package com.greedy.homepage.common.exception;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException {

    private final FailMessage failMessage;

    public Exception(final FailMessage failMessage) {
        super(failMessage.getMessage());
        this.failMessage = failMessage;
    }

    public Exception(final FailMessage failMessage, final Throwable cause) {
        super(failMessage.getMessage(), cause);
        this.failMessage = failMessage;
    }
}
