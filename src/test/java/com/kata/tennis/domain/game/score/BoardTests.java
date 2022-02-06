package com.kata.tennis.domain.game.score;

import com.kata.tennis.domain.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTests {
    private static final Player PLAYER_1 = new Player("Player1");
    private static final Player PLAYER_2 = new Player("Player2");

    @Test
    @DisplayName("Board should return the last score of a player")
    void board_should_return_the_last_score_of_a_player() {
        var board = new Board(PLAYER_1, PLAYER_2);

        Assertions.assertEquals(board.getLastScore(PLAYER_1), Score.ZERO);
        Assertions.assertEquals(board.getLastScore(PLAYER_2), Score.ZERO);
    }

    @Test
    @DisplayName("Board should increment score of a given player")
    void board_should_increment_score_of_a_given_player() {
        var board = new Board(PLAYER_1, PLAYER_2);
        board.increment(PLAYER_1);
        board.increment(PLAYER_1);

        Assertions.assertEquals(board.getLastScore(PLAYER_1), Score.THIRTY);
        Assertions.assertEquals(board.getLastScore(PLAYER_2), Score.ZERO);

        board.increment(PLAYER_2);

        Assertions.assertEquals(board.getLastScore(PLAYER_1), Score.THIRTY);
        Assertions.assertEquals(board.getLastScore(PLAYER_2), Score.FIFTEEN);
    }

    @Test
    @DisplayName("Board should return scores as string")
    void board_should_return_scores_as_string() {
        var board = new Board(PLAYER_1, PLAYER_2);
        board.increment(PLAYER_1);
        board.increment(PLAYER_1);
        board.increment(PLAYER_2);

        var actual = board.toString();
        var expected = String.format("%s : %s \n %s : %s", PLAYER_1.getName(), "0 15 30 30", PLAYER_2.getName(), "0 0 0 15");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Board should check if last scores are equal to a given score")
    void board_should_check_if_last_scores_are_equal_to_a_given_score() {
        var board = new Board(PLAYER_1, PLAYER_2);
        Assertions.assertTrue(board.areScoresEqual(Score.ZERO));

        board.increment(PLAYER_1);
        Assertions.assertFalse(board.areScoresEqual(Score.FIFTEEN));

        board.increment(PLAYER_2);
        Assertions.assertTrue(board.areScoresEqual(Score.FIFTEEN));
    }
}
