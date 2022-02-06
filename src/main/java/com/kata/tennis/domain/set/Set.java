package com.kata.tennis.domain.set;

import com.kata.tennis.domain.Board;
import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.set.game.Game;
import com.kata.tennis.exceptions.set.SetOverException;
import com.kata.tennis.exceptions.set.game.NoSuchGameException;
import com.kata.tennis.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Set {
    private static final int INIT_VALUE = 0;
    private static final int TIE_BREAK_TRIGGER = 6;
    private final List<Game> games;
    private final Board<Player, Integer> board;
    private Player winner;
    private boolean tieBreak;

    public Set(Player player1, Player player2) {
        this.games = new ArrayList<>() {{
            add(new Game(player1, player2));
        }};
        this.board = new Board<>(player1, player2, INIT_VALUE);
    }

    public void score(Player player) {
        if (this.isSetOver(this.winner)) throw new SetOverException();
        this.compute(player, this.board, this.games);
    }

    public Player getWinner() {
        return winner;
    }

    private boolean isSetOver(Player winner) {
        return Objects.nonNull(winner);
    }

    private void compute(Player player, Board<Player, Integer> board, List<Game> games) {
        var lastGame = Optional.ofNullable(ListUtils.getLast(games)).orElseThrow(NoSuchGameException::new);

        if (lastGame.hasWinner()) {
            var game = this.getGame(board);
            games.add(game);
            game.score(player);
        } else {
            lastGame.score(player);
            if (lastGame.hasWinner()) {
                incrementScore(player);
                this.checkTieBreak(board);
                this.checkWinner(player, board, this.tieBreak);
            }
        }
    }

    private void checkTieBreak(Board<Player, Integer> board) {
        this.tieBreak = board.areScoresEqual(TIE_BREAK_TRIGGER);
    }

    private void incrementScore(Player player) {
        var score = this.board.getLastScore(player);
        this.board.append(player, score + 1);
        log.info("***************** Set Score: *****************");
        log.info("{}", this.board);
    }

    private void checkWinner(Player player, Board<Player, Integer> board, boolean tieBreak) {
        var score1 = board.getLastScore(player);
        var score2 = board.getOtherPlayerLastScore(player);
        if (defaultScore(score1, score2, tieBreak) || tieBreakScoreRule(score1, score2, tieBreak)) {
            this.setWinner(player);
        }

    }

    private boolean defaultScore(Integer score1, Integer score2, boolean tieBreak) {
        return ((score1 == 6 && score2 <= 4) || (score1 == 7 && score2 == 5)) && !tieBreak;
    }

    private boolean tieBreakScoreRule(Integer score1, Integer score2, boolean tieBreak) {
        return tieBreak && (score1 - score2 >= 2);
    }

    private void setWinner(Player player) {
        this.winner = player;
        log.info("***************** Game Winner is : {} *****************", player);
    }


    private Game getGame(Board<Player, Integer> board) {
        return new Game(board.getPlayer1(), board.getPlayer2());
    }
}
