package com.RSVP.rsvp.utils;

public class NotEnoughSeatsException extends Exception {

    public NotEnoughSeatsException() {
        super();
    }

    public NotEnoughSeatsException(String message) {
        super(message);
    }

    public NotEnoughSeatsException(String message, Throwable cause) {
        super(message, cause);
    }
}
