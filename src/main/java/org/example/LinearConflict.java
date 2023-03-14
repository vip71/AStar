package org.example;

import static org.example.Manhattan.manhattanForPuzzle;

public class LinearConflict implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init, Puzzle goal) {
    return manhattanForPuzzle(init, goal)+linearConflictValue(init);
}

private int linearConflictValue(Puzzle init) {
    int conflictValue=0;
    //horizontal
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            for(int k=j+1;k<4;k++){
                if(init.fields[i*4+j]<(i+1)*4 && init.fields[i*4+k]>=i*4 && init.fields[i*4+k]<init.fields[i*4+j])
                    conflictValue+=2;
            }
        }
    }
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            for(int k=j+1;k<4;k++){
                if(init.fields[j*4+i]%4 == i && init.fields[k*4+i]%4 == i && init.fields[k*4+i]<init.fields[j*4+i])
                    conflictValue+=2;
            }
        }
    }
    //vertical

    return conflictValue;
}
}
