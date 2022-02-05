package com.kata.tennis.domain.game;

import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.game.score.Board;
import com.kata.tennis.domain.game.score.Score;
import com.kata.tennis.exceptions.GameEndException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Game {
    private final Board board;
    private Player winner;

    public Game(Player player1, Player player2) {
        this.board = new Board(player1, player2);
    }

    public void score(Player player) {
        if (Objects.nonNull(this.winner)) {
            throw new GameEndException();
        }
        if (isWinner(player)) {
            this.winner = player;
            log.info("***************** Game Winner is : {} *****************", player);
        }
        this.board.increment(player);
        log.info("***************** Game Score: *****************");
        log.info("{}", this.board);
    }

    private boolean isWinner(Player player) {
        return this.board.getLastScore(player) == Score.FORTY;
    }

    public Player getWinner() {
        return winner;
    }
}
