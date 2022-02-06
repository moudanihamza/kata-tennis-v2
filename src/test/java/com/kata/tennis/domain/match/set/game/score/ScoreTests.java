package com.kata.tennis.domain.match.set.game.score;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ScoreTests {
    @ParameterizedTest
    @DisplayName("Score should increment")
    @MethodSource("scoreIncrementSupplier")
    public void score_should_increment(Score given, Score expected) {
        Assertions.assertEquals(given.increment(), expected);
    }

    @ParameterizedTest
    @DisplayName("Score should decrement")
    @MethodSource("scoreDecrementSupplier")
    public void score_should_decrement(Score given, Score expected) {
        Assertions.assertEquals(given.decrement(), expected);
    }

    private static Stream<Arguments> scoreIncrementSupplier() {

        return Stream.of(
                Arguments.of(Score.ZERO, Score.FIFTEEN),
                Arguments.of(Score.FIFTEEN, Score.THIRTY),
                Arguments.of(Score.THIRTY, Score.FORTY),
                Arguments.of(Score.FORTY, Score.FORTY)
        );
    }

    private static Stream<Arguments> scoreDecrementSupplier() {

        return Stream.of(
                Arguments.of(Score.ZERO, Score.ZERO),
                Arguments.of(Score.FIFTEEN, Score.ZERO),
                Arguments.of(Score.THIRTY, Score.FIFTEEN),
                Arguments.of(Score.FORTY, Score.THIRTY)
        );
    }
}
