package com.kata.tennis.exceptions.set.game;

public class NoSuchGameException extends RuntimeException {
    private final static String MESSAGE = "No Such game exist";

    public NoSuchGameException() {
        super(MESSAGE);
    }
}
