package com.kata.tennis.domain.game;

import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.game.score.Board;
import com.kata.tennis.domain.game.score.Score;
import com.kata.tennis.exceptions.GameOverException;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


public class GameTests {
    private static final Player PLAYER_1 = new Player("Player1");
    private static final Player PLAYER_2 = new Player("Player2");
    private Board board;
    private Game game;

    @BeforeEach
    void setup() {
        this.board = mock(Board.class);
        this.game = new Game(PLAYER_1, PLAYER_2);
        ReflectionTestUtils.setField(game, "board", board);
    }

    @Test
    @DisplayName("Game should increment the board when a player scores")
    void game_should_increment_the_board_when_a_player_scores() {
        doNothing().when(board).increment(isA(Player.class));
        game.score(PLAYER_1);
        verify(board, times(1)).increment(eq(PLAYER_1));
    }

    @Test
    @DisplayName("Player should win when he scores and his score is FORTY and not in deuce mode")
    void player_should_win_when_he_scores_and_his_score_is_forty_and_not_deuce_mode() {
        when(board.getLastScore(eq(PLAYER_1))).thenReturn(Score.FORTY);
        game.score(PLAYER_1);
        verify(board, times(1)).getLastScore(eq(PLAYER_1));
        Assertions.assertEquals(game.getWinner(), PLAYER_1);
    }

    @Test
    @DisplayName("Game should return exception when it has over")
    void game_should_return_exception_when_it_has_finished() {
        ReflectionTestUtils.setField(game, "winner", PLAYER_1);
        Assertions.assertThrows(GameOverException.class, () -> game.score(PLAYER_1));
    }

    @Test
    @DisplayName("Deuce Mode should be activated when both players have forty")
    void deuce_mode_should_be_activated_when_both_players_have_forty(){
        doNothing().when(board).increment(isA(Player.class));
        when(board.getLastScore(eq(PLAYER_1))).thenReturn(Score.THIRTY);
        when(board.areScoresEqual(eq(Score.FORTY))).thenReturn(true);

        Assertions.assertFalse(game.isDeuceMode());

        game.score(PLAYER_1);
        verify(board).increment(eq(PLAYER_1));
        verify(board).getLastScore(eq(PLAYER_1));
        verify(board).areScoresEqual(eq(Score.FORTY));

        Assertions.assertTrue(game.isDeuceMode());
    }

    @Test
    @DisplayName("Player should win if he has advantage and deuce mode is activated")
    void player_should_win_if_he_has_advantage_and_deuce_mode_is_activated() {
        ReflectionTestUtils.setField(game, "deuceMode", true);
        ReflectionTestUtils.setField(game, "advantage", PLAYER_1);

        game.score(PLAYER_1);
        Assertions.assertEquals(game.getWinner(), PLAYER_1);
    }

    @Test
    @DisplayName("Player should lose advantage if the other one scores")
    void player_should_lose_advantage_if_the_other_one_scores(){
        ReflectionTestUtils.setField(game, "deuceMode", true);
        ReflectionTestUtils.setField(game, "advantage", PLAYER_1);

        game.score(PLAYER_2);
        Assertions.assertNull(game.getWinner());
        Assertions.assertNull(game.getAdvantage());
    }

}
