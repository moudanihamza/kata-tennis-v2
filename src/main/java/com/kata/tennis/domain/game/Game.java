package com.kata.tennis.domain.game;

import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.Board;
import com.kata.tennis.domain.game.score.Score;
import com.kata.tennis.exceptions.GameOverException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Game {
    private final Board<Player, Score> board;
    private Player winner;
    private boolean deuceMode;
    private Player advantage;

    public Game(Player player1, Player player2) {
        this.board = new Board<>(player1, player2, Score.ZERO);
        this.deuceMode = false;
    }

    public void score(Player player) {
        if (this.isGameOver(this.winner)) throw new GameOverException();

        if (gameHasWinner(player, this.board, this.deuceMode, this.advantage)) return;

        computeScore(player);
    }

    public Player getWinner() {
        return winner;
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
            this.deuceMode = this.isDeuceMode(this.board);
        }
    }

    private boolean gameHasWinner(Player player, Board<Player, Score> board, boolean deuceMode, Player advantage) {
        var isWinner = (board.getLastScore(player) == Score.FORTY && !deuceMode) || (advantage == player && deuceMode);
        if (isWinner) {
            this.winner = player;
            log.info("***************** Game Winner is : {} *****************", player);
        }
        return Objects.nonNull(this.winner);
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
        return Objects.isNull(advantage) ? player : null;
    }


    private boolean isDeuceMode(Board<Player, Score> board) {
        return board.areScoresEqual(Score.FORTY);
    }
}
