package com.kata.tennis.domain.match;

import com.kata.tennis.domain.Board;
import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.match.set.Set;
import com.kata.tennis.exceptions.match.MatchOverException;
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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class MatchTests {
    private static final Player PLAYER_1 = new Player("Player1");
    private static final Player PLAYER_2 = new Player("Player2");
    private Board<Player, Integer> board;
    private List<Set> sets;
    private Match match;

    @BeforeEach
    void setup() {
        this.board = mock(Board.class);
        this.sets = mock(List.class);
        this.match = new Match(PLAYER_1, PLAYER_2);
        ReflectionTestUtils.setField(this.match, "board", this.board);
        ReflectionTestUtils.setField(this.match, "sets", this.sets);
    }

    @Test
    @DisplayName("Match should throw an exception when it has a winner")
    void set_should_throw_an_exception_when_it_has_a_winner() {
        ReflectionTestUtils.setField(this.match, "winner", PLAYER_1);
        Assertions.assertThrows(MatchOverException.class, () -> this.match.score(PLAYER_1));
    }

    @Test
    @DisplayName("Match should start a new set when the previous is over and there is no winner")
    void set_should_start_a_new_game_when_the_previous_is_over_and_there_is_no_winner() {
        var set = mock(Set.class);
        when(set.hasWinner()).thenReturn(true);
        when(this.sets.get(isA(Integer.class))).thenReturn(set);
        when(this.sets.add(isA(Set.class))).thenReturn(true);
        when(this.board.getPlayer1()).thenReturn(PLAYER_1);
        when(this.board.getPlayer2()).thenReturn(PLAYER_2);

        this.match.score(PLAYER_1);

        verify(set).hasWinner();
        verify(this.sets).get(isA(Integer.class));
        verify(this.sets).add(isA(Set.class));
    }

    @ParameterizedTest
    @DisplayName("Set should increment score board when a player win the set")
    @MethodSource("setCreationTestArgsSupplier")
    void set_should_increment_score_board_when_a_player_win_the_set(Player player, int givenPlayer1Score, int givenPlayer2Score) {
        var set = mock(Set.class);
        var player1NewScore = givenPlayer1Score + 1;
        when(set.hasWinner()).thenReturn(false).thenReturn(true);
        when(this.sets.get(isA(Integer.class))).thenReturn(set);
        when(this.board.getLastScore(eq(player))).thenReturn(givenPlayer1Score).thenReturn(player1NewScore);
        doNothing().when(this.board).append(eq(player), eq(player1NewScore));

        this.match.score(PLAYER_1);

        verify(set, times(2)).hasWinner();
        verify(this.sets).get(isA(Integer.class));
        verify(this.board, times(2)).getLastScore(eq(player));
        verify(this.board).append(eq(player), eq(player1NewScore));

        Assertions.assertNull(this.match.getWinner());
    }

    @ParameterizedTest
    @DisplayName("Player should win the match if conditions are matched")
    @MethodSource("winTestArgsSupplier")
    void player_should_win_the_match_if_condition_are_matched(Player player, int givenPlayer1Score) {
        var set = mock(Set.class);
        var player1NewScore = givenPlayer1Score + 1;
        when(set.hasWinner()).thenReturn(false).thenReturn(true);
        when(this.sets.get(isA(Integer.class))).thenReturn(set);
        when(this.board.getLastScore(eq(player))).thenReturn(givenPlayer1Score).thenReturn(player1NewScore);
        doNothing().when(this.board).append(eq(player), eq(player1NewScore));

        Assertions.assertNull(this.match.getWinner());

        this.match.score(player);

        verify(set, times(2)).hasWinner();
        verify(this.sets).get(isA(Integer.class));
        verify(this.board, times(2)).getLastScore(eq(player));
        verify(this.board).append(eq(player), eq(player1NewScore));

        Assertions.assertEquals(player, this.match.getWinner());
    }

    private static Stream<Arguments> setCreationTestArgsSupplier() {

        return Stream.of(
                Arguments.of(PLAYER_1, 0, 0),
                Arguments.of(PLAYER_1, 0, 1)
        );
    }

    private static Stream<Arguments> winTestArgsSupplier() {

        return Stream.of(
                Arguments.of(PLAYER_1, 1),
                Arguments.of(PLAYER_2, 1)
        );
    }
}
