package com.kata.tennis.exceptions.match.set;

public class NoSuchSetException extends RuntimeException {
    private final static String MESSAGE = "No Such set exist";

    public NoSuchSetException() {
        super(MESSAGE);
    }
}
