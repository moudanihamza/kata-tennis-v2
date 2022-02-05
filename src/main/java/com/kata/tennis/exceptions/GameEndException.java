package com.kata.tennis.exceptions;

public class GameEndException extends RuntimeException {
    private final static String MESSAGE = "Game has finished";

    public GameEndException() {
        super(MESSAGE);
    }
}
