package com.kata.tennis.domain.game.score;


import com.kata.tennis.domain.Player;
import com.kata.tennis.exceptions.NoSuchPlayerException;
import com.kata.tennis.utils.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final Tuple<Player, List<Score>> row1;
    private final Tuple<Player, List<Score>> row2;

    public Board(Player player1, Player player2) {
        this.row1 = this.initRow(player1);
        this.row2 = this.initRow(player2);
    }

    public void increment(Player player) {
        this.incrementPlayerScore(player);
        this.duplicatePlayerScore(this.getOtherPlayerName(player));
    }

    public Score getLastScore(Player player) {
        var scores = this.findScores(player);
        return scores.get(scores.size() - 1);
    }

    @Override
    public String toString() {
        return String.format("%s : %s \n %s : %s",
                this.row1.getLeft().getName(),
                this.listAsString(this.row1.getRight()),
                this.row2.getLeft().getName(),
                this.listAsString(this.row2.getRight())
        );
    }

    private void incrementPlayerScore(Player player) {
        var scores = this.findScores(player);
        var score = this.getLastScore(player);
        scores.add(score.increment());
    }

    private void duplicatePlayerScore(Player player) {
        var scores = this.findScores(player);
        var score = this.getLastScore(player);
        scores.add(score);
    }

    private Player getOtherPlayerName(Player player) {
        var player1 = this.row1.getLeft();
        var player2 = this.row2.getLeft();
        return player1.equals(player) ? player2 : player1;
    }

    private Tuple<Player, List<Score>> initRow(Player player1) {
        return new Tuple<>(player1, getInitialScoreList());
    }

    private ArrayList<Score> getInitialScoreList() {
        return new ArrayList<Score>() {{
            add(Score.ZERO);
        }};
    }


    private String listAsString(List<Score> scores) {
        return scores.stream().map(Score::getValue).collect(Collectors.joining(" "));
    }

    private List<Score> findScores(Player player) {
        if (this.row1.getLeft().equals(player)) {
            return this.row1.getRight();
        } else if (this.row2.getLeft().equals(player)) {
            return this.row2.getRight();
        }
        throw new NoSuchPlayerException();
    }

}
