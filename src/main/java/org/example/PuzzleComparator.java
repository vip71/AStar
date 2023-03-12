package org.example;

import java.util.Comparator;

public class PuzzleComparator implements Comparator<Puzzle> {
@Override
public int compare(Puzzle s1, Puzzle s2) {
    if (s1.getHeuristicValue() < (s2.getHeuristicValue())) return -1;
    if (s1.getHeuristicValue() > (s2.getHeuristicValue())) return 1;
    return 0;
}
}
