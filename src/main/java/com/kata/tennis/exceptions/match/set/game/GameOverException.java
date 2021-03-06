package com.kata.tennis.exceptions.match.set.game;

public class GameOverException extends RuntimeException {
    private final static String MESSAGE = "Game is over";

    public GameOverException() {
        super(MESSAGE);
    }
}
