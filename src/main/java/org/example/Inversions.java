package org.example;

public class Inversions implements Heuristic{
@Override
public int getHeuristicValue(Puzzle init) {
    return countInversions(init.fields);
}

private int countVerticalInversions(byte[] table) {
    byte[] tableRearranged =new byte[16];
    for(int i=0;i<4;i++) {
        for (int j = 0; j < 4; j++) {
            tableRearranged[j * 4 + i] = table[i * 4 + j];
        }
    }
    //zmienic
    return countInversions(tableRearranged);
}

private int countInversions(byte[] array){
    int inversions = 0;
    for(int i=15;i>=0;i--){
        for(int j=i;j>=0;j--){
            if(array[j] != 0 && array[i] != 0 && array[j] > array[i]) inversions++;
        }
    }
    return inversions/3+inversions%3;
}



}
