package com.kata.tennis.domain.match;

import com.kata.tennis.domain.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class MatchIT {

    @Test
    @DisplayName("Player 1 should win the match")
    void player1_should_win_the_match(){
        var player1 = new Player("player1");
        var player2 = new Player("player2");
        var match  = new Match(player1,player2);
        IntStream.range(0,48).forEachOrdered(i->match.score(player1));
        Assertions.assertEquals(player1, match.getWinner());
    }
}
