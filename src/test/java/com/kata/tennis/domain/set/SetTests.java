package com.kata.tennis.domain.set;

import com.kata.tennis.domain.Board;
import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.set.game.Game;
import com.kata.tennis.exceptions.set.SetOverException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class SetTests {
    private static final Player PLAYER_1 = new Player("Player1");
    private static final Player PLAYER_2 = new Player("Player2");
    private Board<Player, Integer> board;
    private List<Game> games;
    private Set set;

    @BeforeEach
    void setup() {
        this.board = mock(Board.class);
        this.games = mock(List.class);
        this.set = new Set(PLAYER_1, PLAYER_2);
        ReflectionTestUtils.setField(this.set, "board", this.board);
        ReflectionTestUtils.setField(this.set, "games", this.games);
    }

    @Test
    @DisplayName("Set should throw an exception when it has a winner")
    void set_should_throw_an_exception_when_it_has_a_winner() {
        ReflectionTestUtils.setField(this.set, "winner", PLAYER_1);
        Assertions.assertThrows(SetOverException.class, () -> this.set.score(PLAYER_1));
    }

    @Test
    @DisplayName("Set should start a new game when the previous is over and there is no winner")
    void set_should_start_a_new_game_when_the_previous_is_over_and_there_is_no_winner() {
        var game = mock(Game.class);
        when(game.hasWinner()).thenReturn(true);
        when(this.games.get(isA(Integer.class))).thenReturn(game);
        when(this.games.add(isA(Game.class))).thenReturn(true);
        when(this.board.getPlayer1()).thenReturn(PLAYER_1);
        when(this.board.getPlayer2()).thenReturn(PLAYER_2);

        set.score(PLAYER_1);

        verify(game).hasWinner();
        verify(this.games).get(isA(Integer.class));
        verify(this.games).add(isA(Game.class));
    }

    @ParameterizedTest
    @DisplayName("Set should increment score board when a player win the game")
    @MethodSource("gameCreationTestArgsSupplier")
    void set_should_increment_score_board_when_a_player_win_the_game(Player player, int givenPlayer1Score, int givenPlayer2Score) {
        var game = mock(Game.class);
        var player1NewScore = givenPlayer1Score + 1;
        when(game.hasWinner()).thenReturn(false).thenReturn(true);
        when(this.games.get(isA(Integer.class))).thenReturn(game);
        when(this.board.getLastScore(eq(player))).thenReturn(givenPlayer1Score).thenReturn(player1NewScore);
        doNothing().when(this.board).append(eq(player), eq(player1NewScore));
        when(this.board.getOtherPlayerLastScore(eq(player))).thenReturn(givenPlayer2Score);

        set.score(PLAYER_1);

        verify(game, times(2)).hasWinner();
        verify(this.games).get(isA(Integer.class));
        verify(this.board, times(2)).getLastScore(eq(player));
        verify(this.board).append(eq(player), eq(player1NewScore));
        verify(this.board).getOtherPlayerLastScore(eq(player));

        Assertions.assertNull(this.set.getWinner());
    }

    @ParameterizedTest
    @DisplayName("Player should win the set if conditions are matched")
    @MethodSource("winTestArgsSupplier")
    void player_should_win_the_set_if_condition_are_matched(Player player, int givenPlayer1Score, int givenPlayer2Score, boolean activateTieBreak) {
        var game = mock(Game.class);
        var player1NewScore = givenPlayer1Score + 1;
        when(game.hasWinner()).thenReturn(false).thenReturn(true);
        when(this.games.get(isA(Integer.class))).thenReturn(game);
        when(this.board.getLastScore(eq(player))).thenReturn(givenPlayer1Score).thenReturn(player1NewScore);
        doNothing().when(this.board).append(eq(player), eq(player1NewScore));
        when(this.board.getOtherPlayerLastScore(eq(player))).thenReturn(givenPlayer2Score);
        when(this.board.areScoresEqual(any())).thenReturn(activateTieBreak);

        Assertions.assertNull(this.set.getWinner());

        set.score(player);

        verify(game, times(2)).hasWinner();
        verify(this.games).get(isA(Integer.class));
        verify(this.board, times(2)).getLastScore(eq(player));
        verify(this.board).append(eq(player), eq(player1NewScore));
        verify(this.board).getOtherPlayerLastScore(eq(player));

        Assertions.assertEquals(player, this.set.getWinner());
    }

    private static Stream<Arguments> gameCreationTestArgsSupplier() {

        return Stream.of(
                Arguments.of(PLAYER_1, 0, 0),
                Arguments.of(PLAYER_1, 1, 2),
                Arguments.of(PLAYER_1, 2, 2),
                Arguments.of(PLAYER_1, 3, 4),
                Arguments.of(PLAYER_1, 4, 5),
                Arguments.of(PLAYER_1, 5, 5),
                Arguments.of(PLAYER_1, 5, 6),
                Arguments.of(PLAYER_1, 6, 6),
                Arguments.of(PLAYER_1, 13, 12)
        );
    }

    private static Stream<Arguments> winTestArgsSupplier() {

        return Stream.of(
                Arguments.of(PLAYER_1, 5, 0,false),
                Arguments.of(PLAYER_1, 5, 3,false),
                Arguments.of(PLAYER_1, 5, 4,false),
                Arguments.of(PLAYER_1, 7, 6,true),
                Arguments.of(PLAYER_1, 11, 10,true)
        );
    }
}
