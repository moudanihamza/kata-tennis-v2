package com.kata.tennis.utils;

import java.util.List;

public class ListUtils {
    public static <U> U getLast(List<U> list) {
       return  list.isEmpty() ? null : list.get(list.size() - 1);
    }
}
