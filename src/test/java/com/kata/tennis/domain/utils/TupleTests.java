package com.kata.tennis.domain.utils;

import com.kata.tennis.utils.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TupleTests {
    @Test
    void tupleTestShouldBeSuccessful(){

        Tuple<Integer, Integer> tuple = new Tuple<>(1,2);

        Assertions.assertEquals(tuple.getLeft(),1);

        Assertions.assertEquals(tuple.getRight(),2);

        Assertions.assertEquals(tuple,new Tuple<>(1,2));

        Assertions.assertNotEquals(tuple,new Tuple<>(1,3));
    }
}
