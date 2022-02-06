package com.kata.tennis.exceptions;

public class GameOverException extends RuntimeException {
    private final static String MESSAGE = "Game has finished";

    public GameOverException() {
        super(MESSAGE);
    }
}
