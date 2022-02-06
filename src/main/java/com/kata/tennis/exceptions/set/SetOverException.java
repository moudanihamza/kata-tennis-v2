package com.kata.tennis.exceptions.set;

public class SetOverException extends RuntimeException {
    private final static String MESSAGE = "Set is over";

    public SetOverException() {
        super(MESSAGE);
    }
}
