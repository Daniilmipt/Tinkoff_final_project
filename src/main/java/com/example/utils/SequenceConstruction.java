package com.example.utils;

import java.util.ArrayList;
import java.util.List;

public class SequenceConstruction {

    public static List<List<Integer>> generateSequences(List<Integer> list) {
        List<List<Integer>> result = new ArrayList<>();
        generateSequencesHelper(list, 0, new ArrayList<>(), result);
        return result;
    }

    private static void generateSequencesHelper(List<Integer> list, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == list.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < list.get(index); i++) {
            current.add(i);
            generateSequencesHelper(list, index + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}

