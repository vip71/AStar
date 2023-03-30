package org.example;

import static java.lang.Math.abs;

public class Manhattan implements Heuristic{

@Override
public int getHeuristicValue(Puzzle init) {
    return manhattanForPuzzle(init);
}

public static int manhattanForPuzzle(Puzzle init){
    int diff =0;
    for(int i=1;i<16;i++){
        int a = findNumber(i, init);
        diff+=manhattanDistance(a,i-1);
    }
    return diff;
}

public static int findNumber(int number,Puzzle puzzle){
    for(int i=0;i<15;i++){
        if(puzzle.fields[i]==number) return i;
    }
    return 15;
}

public static int manhattanDistance(int a, int b){
    return abs(a%4-b%4)+abs(a/4-b/4);
}
}
