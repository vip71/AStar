package org.example;

import static java.lang.Math.abs;

public class Manhattan implements Heuristic{

@Override
public int getHeuristicValue(Puzzle init, Puzzle goal) {
    return manhattanForPuzzle(init, goal);
}

public static int manhattanForPuzzle(Puzzle init, Puzzle goal) {
    if(init == null || goal == null)
        return Integer.MAX_VALUE;
    int diff =0;
    for(int i=1;i<16;i++){//0 nie 1
        int a = findNumber(i, init);
        int b = findNumber(i, goal);
        diff+=manhattanDistance(a,b);//     /2
    }
    return diff/2;
}

public static int findNumber(int number,Puzzle puzzle){
    for(int i=1;i<16;i++){
        if(puzzle.fields[i]==number) return i;
    }
    return -1;
}

public static int manhattanDistance(int a, int b){
    return abs(a%4-b%4)+abs(a/4-b/4);
}
}
