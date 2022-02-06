package com.kata.tennis.domain.match;

import com.kata.tennis.domain.Board;
import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.match.set.Set;
import com.kata.tennis.exceptions.match.MatchOverException;
import com.kata.tennis.exceptions.match.set.NoSuchSetException;
import com.kata.tennis.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Match {
    private static final int INIT_VALUE = 0;
    private final Board<Player, Integer> board;
    private final List<Set> sets;
    private Player winner;

    public Match(Player player1, Player player2) {
        this.board = new Board<>(player1, player2, INIT_VALUE);
        this.sets = new ArrayList<>() {{
            add(new Set(player1, player2));
        }};
    }

    public void score(Player player) {
        if (this.isMatchOver(this.winner)) throw new MatchOverException();
        this.compute(player, this.board, this.sets);
    }

    private boolean isMatchOver(Player winner) {
        return Objects.nonNull(winner);
    }

    private void compute(Player player, Board<Player, Integer> board, List<Set> sets) {
        var lastSet = Optional.ofNullable(ListUtils.getLast(sets)).orElseThrow(NoSuchSetException::new);
        if (lastSet.hasWinner()) {
            var set = new Set(board.getPlayer1(), board.getPlayer2());
            sets.add(set);
            set.score(player);
        } else {
            lastSet.score(player);
            if (lastSet.hasWinner()) {
                this.incrementScore(player);
                this.checkWinner(player, board);
            }
        }
    }


    private void incrementScore(Player player) {
        var score = this.board.getLastScore(player);
        this.board.append(player, score + 1);
        log.info("***************** Match Score: *****************");
        log.info("{}", this.board);
    }

    private void checkWinner(Player player, Board<Player, Integer> board) {
        if(board.getLastScore(player)>=2){
            this.setWinner(player);
        }
    }
    private void setWinner(Player player) {
        this.winner = player;
        log.info("***************** Match Winner is : {} *****************", player);
    }

    public Player getWinner() {
        return winner;
    }
}
