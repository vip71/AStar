package org.example;

public class Inversions implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init, Puzzle goal) {
    return countInversions(init.fields)+countVerticalInversions(init.fields);
}

private int countVerticalInversions(int table[]) {
    int tableRearranged[]=new int[16];
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            tableRearranged[i*4+j]=table[j*4+i];
        }
    }
    return countInversions(tableRearranged);
}

private int countInversions(int array[]){
    int inversions = 0;
    for(int i=0;i<16;i++){
        for(int j=0;j<i;j++){
            if(array[i] != 0 && array[i] < array[j]) inversions++;
        }
    }
    return inversions/3+inversions%3;
}



}
