package com.kata.tennis.domain;


import com.kata.tennis.exceptions.NoSuchPlayerException;
import com.kata.tennis.utils.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class Board<U, V> {
    private final Tuple<U, List<V>> row1;
    private final Tuple<U, List<V>> row2;

    public Board(U player1, U player2, V initValue) {
        this.row1 = this.initRow(player1, initValue);
        this.row2 = this.initRow(player2, initValue);
    }

    public void append(U player,V value) {
        this.appendPlayerScore(player, value);
        this.duplicatePlayerScore(this.getOtherPlayerName(player));
    }

    public V getLastScore(U player) {
        var scores = this.findScores(player);
        return scores.get(scores.size() - 1);
    }

    public boolean areScoresEqual(V score) {
        return this.getLastScore(row1.getLeft()) == score && this.getLastScore(row2.getLeft()) == score;
    }

    @Override
    public String toString() {
        return String.format("%s : %s \n %s : %s",
                this.row1.getLeft(),
                this.listAsString(this.row1.getRight()),
                this.row2.getLeft(),
                this.listAsString(this.row2.getRight())
        );
    }

    private void appendPlayerScore(U player, V value) {
        var scores = this.findScores(player);
        scores.add(value);
    }

    private void duplicatePlayerScore(U player) {
        var scores = this.findScores(player);
        var score = this.getLastScore(player);
        scores.add(score);
    }

    private U getOtherPlayerName(U player) {
        var player1 = this.row1.getLeft();
        var player2 = this.row2.getLeft();
        return player1.equals(player) ? player2 : player1;
    }

    private Tuple<U, List<V>> initRow(U player1, V initValue) {
        return new Tuple<>(player1, getInitialScoreList(initValue));
    }

    private ArrayList<V> getInitialScoreList(V initValue) {
        return new ArrayList<V>() {{
            add(initValue);
        }};
    }


    private String listAsString(List<V> scores) {
        return scores.stream().map(V::toString).collect(Collectors.joining(" "));
    }

    private List<V> findScores(U player) {
        if (this.row1.getLeft().equals(player)) {
            return this.row1.getRight();
        } else if (this.row2.getLeft().equals(player)) {
            return this.row2.getRight();
        }
        throw new NoSuchPlayerException();
    }

}
