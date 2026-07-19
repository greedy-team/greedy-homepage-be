package com.greedy.homepage.common.exception;

import lombok.Getter;

@Getter
public class HomepageException extends RuntimeException {

    private final FailMessage failMessage;

    public HomepageException(final FailMessage failMessage) {
        super(failMessage.getMessage());
        this.failMessage = failMessage;
    }

    public HomepageException(final FailMessage failMessage, final Throwable cause) {
        super(failMessage.getMessage(), cause);
        this.failMessage = failMessage;
    }
}
