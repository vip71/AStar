package org.example;

import static org.example.Manhattan.manhattanForPuzzle;

public class LinearConflict implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init) {
    return manhattanForPuzzle(init)+linearConflictValue(init);
}

public static int linearConflictValue(Puzzle init) {
    int conflictValue=0;
    //horizontal
    int smaller,bigger;
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            smaller = init.fields[i*4+j];
            for(int k=j+1;k<4;k++){
                bigger = init.fields[i*4+k];
                if(smaller!=0 && bigger!=0 && smaller<=(i+1)*4 && smaller>i*4 && bigger<=(i+1)*4 && bigger>i*4 && bigger<smaller)
                    conflictValue+=2;
            }
        }
    }
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            smaller = init.fields[j*4+i];
            for(int k=j+1;k<4;k++){
                bigger = init.fields[k*4+i];
                if(smaller!=0 && bigger!=0 && smaller%4 == i+1 && bigger%4 == i+1 && bigger<smaller)
                    conflictValue+=2;
            }
        }
    }
    //vertical

    return conflictValue;
}
}
