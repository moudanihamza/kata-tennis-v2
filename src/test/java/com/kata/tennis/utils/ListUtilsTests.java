package com.kata.tennis.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ListUtilsTests {

    @Test
    @DisplayName("List should return the last element")
    void list_should_return_the_last_element() {
        var list = new ArrayList<>();
        Assertions.assertNull(ListUtils.getLast(list));

        list.add(1);
        Assertions.assertEquals(1,ListUtils.getLast(list));

        list.add(2);
        list.add(3);
        Assertions.assertEquals(3,ListUtils.getLast(list));



    }
}
