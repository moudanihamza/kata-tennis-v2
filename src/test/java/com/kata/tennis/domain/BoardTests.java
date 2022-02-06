package com.kata.tennis.domain;

import com.kata.tennis.domain.set.game.score.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTests {
    private static final Player PLAYER_1 = new Player("Player1");
    private static final Player PLAYER_2 = new Player("Player2");

    @Test
    @DisplayName("Board should return the last score of a player")
    void board_should_return_the_last_score_of_a_player() {
        var board = new Board<>(PLAYER_1, PLAYER_2, Score.ZERO);

        Assertions.assertEquals(board.getLastScore(PLAYER_1), Score.ZERO);
        Assertions.assertEquals(board.getLastScore(PLAYER_2), Score.ZERO);
    }

    @Test
    @DisplayName("Board should return the last score of the other player")
    void board_should_return_the_last_score_of_the_other_player() {
        var board = new Board<>(PLAYER_1, PLAYER_2, Score.ZERO);

        Assertions.assertEquals(board.getOtherPlayerLastScore(PLAYER_1), Score.ZERO);
        Assertions.assertEquals(board.getOtherPlayerLastScore(PLAYER_2), Score.ZERO);

        board.append(PLAYER_2,Score.FIFTEEN);

        Assertions.assertEquals(board.getOtherPlayerLastScore(PLAYER_1), Score.FIFTEEN);
        Assertions.assertEquals(board.getOtherPlayerLastScore(PLAYER_2), Score.ZERO);
    }

    @Test
    @DisplayName("Board should increment score of a given player")
    void board_should_increment_score_of_a_given_player() {
        var board = new Board<>(PLAYER_1, PLAYER_2, Score.ZERO);
        board.append(PLAYER_1, Score.ZERO.increment());
        board.append(PLAYER_1, Score.FIFTEEN.increment());

        Assertions.assertEquals(board.getLastScore(PLAYER_1), Score.THIRTY);
        Assertions.assertEquals(board.getLastScore(PLAYER_2), Score.ZERO);

        board.append(PLAYER_2, Score.ZERO.increment());

        Assertions.assertEquals(board.getLastScore(PLAYER_1), Score.THIRTY);
        Assertions.assertEquals(board.getLastScore(PLAYER_2), Score.FIFTEEN);
    }

    @Test
    @DisplayName("Board should return scores as string")
    void board_should_return_scores_as_string() {
        var board = new Board<>(PLAYER_1, PLAYER_2, Score.ZERO);
        board.append(PLAYER_1, Score.ZERO.increment());
        board.append(PLAYER_1, Score.FIFTEEN.increment());
        board.append(PLAYER_2, Score.ZERO.increment());

        var actual = board.toString();
        var expected = String.format("%s : %s \n %s : %s", PLAYER_1.getName(), "0 15 30 30", PLAYER_2.getName(), "0 0 0 15");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Board should check if last scores are equal to a given score")
    void board_should_check_if_last_scores_are_equal_to_a_given_score() {
        var board = new Board<>(PLAYER_1, PLAYER_2, Score.ZERO);
        Assertions.assertTrue(board.areScoresEqual(Score.ZERO));

        board.append(PLAYER_1, Score.ZERO.increment());
        Assertions.assertFalse(board.areScoresEqual(Score.FIFTEEN));

        board.append(PLAYER_2, Score.ZERO.increment());
        Assertions.assertTrue(board.areScoresEqual(Score.FIFTEEN));
    }

    @Test
    @DisplayName("Board should return players")
    void board_should_return_players(){
        var board = new Board<>(PLAYER_1, PLAYER_2, Score.ZERO);
        Assertions.assertEquals(board.getPlayer1(),PLAYER_1);
        Assertions.assertEquals(board.getPlayer2(),PLAYER_2);

        board = new Board<>(PLAYER_2, PLAYER_1, Score.ZERO);
        Assertions.assertEquals(board.getPlayer1(),PLAYER_2);
        Assertions.assertEquals(board.getPlayer2(),PLAYER_1);
    }
}
