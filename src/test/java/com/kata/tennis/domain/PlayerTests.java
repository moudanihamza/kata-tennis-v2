package com.kata.tennis.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerTests {

    @Test
    @DisplayName("Player should return the right name")
    void player_should_return_the_right_name(){
        var player = new Player("foo");
        Assertions.assertEquals("foo",player.getName());
    }

    @Test
    @DisplayName("To string method should return player name")
    void toString_should_return_player_name(){
        var player = new Player("foo");
        Assertions.assertEquals("foo",player.toString());
    }
}
