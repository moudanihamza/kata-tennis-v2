package com.kata.tennis.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Tuple<X, Y> {
    private X left;
    private Y right;
}