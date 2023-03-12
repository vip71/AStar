package org.example;

import static java.lang.Math.abs;

public class GoodHeuristic implements Heuristic{

@Override
public int getHeuristicValue(Puzzle init, Puzzle goal) {
    if(init == null || goal == null)
        return Integer.MAX_VALUE;
    int diff =0;
    for(int i=0;i<16;i++){
        int a = findNumber(i,init);
        int b = findNumber(i,goal);
        diff+=manhattanDistance(a,b);
    }
    return diff;
}

public int findNumber(int number,Puzzle puzzle){
    for(int i=1;i<16;i++){
        if(puzzle.field[i]==number) return i;
    }
    return -1;
}

public int manhattanDistance(int a, int b){
    return abs(a%4-b%4)+abs(a/4-b/4);
}
}
