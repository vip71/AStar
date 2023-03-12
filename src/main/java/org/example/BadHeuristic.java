package org.example;

public class BadHeuristic implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init, Puzzle goal) {
    if(init == null || goal == null)
        return Integer.MAX_VALUE;
    int diff = 0;
    for (int i = 0; i < 16; i++)
        if (init.field[i] != goal.field[i]){
            diff++;
        }
    return diff;
}
}
