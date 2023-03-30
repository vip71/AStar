package org.example;

public class ZeroHeuristic implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init) {
    return 0;
}
}
