package org.example;

public class LinearConflictAndManhattan implements Heuristic {
@Override
public int getHeuristicValue(Puzzle init, Puzzle goal) {
    GoodHeuristic manhattan = new GoodHeuristic();
    //TODO: TO BE ADDED
    return manhattan.getHeuristicValue(init,goal);
}
}
