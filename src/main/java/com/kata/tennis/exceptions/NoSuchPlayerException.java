package com.kata.tennis.exceptions;

public class NoSuchPlayerException extends RuntimeException{
    private static  final String MESSAGE = "No such player founded ";
    public NoSuchPlayerException() {
        super(MESSAGE);
    }
}
