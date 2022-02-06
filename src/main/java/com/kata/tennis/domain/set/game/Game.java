package com.kata.tennis.domain.set.game;

import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.Board;
import com.kata.tennis.domain.set.game.score.Score;
import com.kata.tennis.exceptions.set.game.GameOverException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Game {
    private static final Score INIT_VALUE = Score.ZERO;
    private final Board<Player, Score> board;
    private Player winner;
    private boolean deuceMode;
    private Player advantage;

    public Game(Player player1, Player player2) {
        this.board = new Board<>(player1, player2, INIT_VALUE);
        this.setDeuceMode(false);
    }

    public void score(Player player) {
        if (this.isGameOver(this.winner)) throw new GameOverException();

        if (gameHasWinner(player, this.board, this.deuceMode, this.advantage)) return;

        computeScore(player);
    }

    public Player getWinner() {
        return winner;
    }

    public boolean hasWinner() {
        return Objects.nonNull(this.winner);
    }

    public Player getAdvantage() {
        return advantage;
    }

    public boolean isDeuceMode() {
        return deuceMode;
    }

    private void computeScore(Player player) {
        if (this.deuceMode) {
            this.advantage = this.computeAdvantage(player, this.advantage);
        } else {
            this.incrementScore(player, this.board);
            this.setDeuceMode(this.isDeuceMode(this.board));
        }
    }

    private void setDeuceMode(boolean deuceMode) {
        this.deuceMode = deuceMode;
        log.info("***************** Deuce Mode Activated : {} *****************", this.deuceMode);
    }

    private boolean gameHasWinner(Player player, Board<Player, Score> board, boolean deuceMode, Player advantage) {
        var isWinner = (board.getLastScore(player) == Score.FORTY && !deuceMode) || (advantage == player && deuceMode);
        if (isWinner) {
            this.winner = player;
            log.info("***************** Game Winner is : {} *****************", player);
        }
        return this.hasWinner();
    }

    private boolean isGameOver(Player winner) {
        return Objects.nonNull(winner);
    }

    private void incrementScore(Player player, Board<Player, Score> board) {
        var score = board.getLastScore(player);
        board.append(player, score.increment());
        log.info("***************** Game Score: *****************");
        log.info("{}", this.board);
    }

    private Player computeAdvantage(Player player, Player advantage) {
        if(Objects.isNull(advantage)) {
            log.info("***************** Player {} win Advantage *****************", player);
            return player;
        }
        return null;
    }


    private boolean isDeuceMode(Board<Player, Score> board) {
        return board.areScoresEqual(Score.FORTY);
    }
}
