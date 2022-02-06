package com.kata.tennis.domain;


import com.kata.tennis.exceptions.NoSuchPlayerException;
import com.kata.tennis.utils.ListUtils;
import com.kata.tennis.utils.Tuple;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

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
        return ListUtils.getLast(scores);
    }

    public V getOtherPlayerLastScore(U player){
        var scores = this.findScores(this.getOtherPlayerName(player));
        return ListUtils.getLast(scores);
    }

    public boolean areScoresEqual(V score) {
        return this.getLastScore(row1.getLeft()) == score && this.getLastScore(row2.getLeft()) == score;
    }

    public U getPlayer1() {
        return  this.row1.getLeft();
    }

    public U getPlayer2() {
        return  this.row2.getLeft();
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
       return Strings.join(scores, ' ');
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
