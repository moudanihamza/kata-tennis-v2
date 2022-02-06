package com.kata.tennis.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;


@Value
@EqualsAndHashCode
public class Player {
    String name;

    @Override
    public String toString() {
        return name;
    }
}
