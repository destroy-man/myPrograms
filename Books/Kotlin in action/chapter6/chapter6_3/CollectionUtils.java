package ru.korobeynikov.chapter6.chapter6_3;

import java.util.List;

public class CollectionUtils {
    public static List<String> uppercaseAll(List<String> items) {
        items.replaceAll(String::toUpperCase);
        return items;
    }
}
