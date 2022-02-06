package com.kata.tennis.exceptions.match;

public class MatchOverException extends RuntimeException{
    private final static String MESSAGE = "Match is over";

    public MatchOverException() {
        super(MESSAGE);
    }
}
