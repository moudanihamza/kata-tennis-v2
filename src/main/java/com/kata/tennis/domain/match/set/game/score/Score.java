package com.kata.tennis.domain.match.set.game.score;

/**
 * /!\ please don't change the order /!\
 */
public enum Score {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40");

    private final String value;

    private static final Score[] SCORES = values();

    Score(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }

    public Score increment() {
        return this.ordinal() == SCORES.length - 1 ? this : SCORES[ordinal() + 1];
    }

    public Score decrement() {
        return this.ordinal() == 0 ? this : SCORES[ordinal() - 1];
    }

    @Override
    public String toString() {
        return value;
    }
}