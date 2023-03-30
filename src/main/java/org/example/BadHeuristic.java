package org.example;

public class BadHeuristic implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init) {
    int diff = 0;
    for (int i = 0; i < 16; i++)
        if (init.fields[i] != i){
            diff++;
        }
    return diff;
}
}
