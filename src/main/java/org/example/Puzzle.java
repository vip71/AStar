package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
public class Puzzle {
private byte emptyPos;
private byte emptyRow;
private byte emptyCol;
public byte[] fields;

public int manhattan;

public int F;
public void setF(int i){
    F = i;
}

public int getF(){
    return F;
}

private int G;

public void setG(int i){
    G = i;
}

public int getG(){
    return G;
}

private int H;

public void setH(int i){
    H = i;
}

public int getH(){
    return H;
}

public void reset(boolean isStart){
    if(isStart){
        setG(0);
        setH(Main.heuristic.getHeuristicValue(this));
        setF(G+H);
    }
    else {
        setG(Integer.MAX_VALUE);
        setH(Main.heuristic.getHeuristicValue(this));
        setF(G+H);
    }
}

Puzzle(){
    fields = new byte[16];
    for(int i = 0; i < 16 ; i++)
    {
        fields[i] = (byte) ((i+1)%16);
    }
    countEmptyPosition();
    this.G = Integer.MAX_VALUE;
    emptyRow=3;
    emptyCol=3;
    manhattan=0;
}

Puzzle(Puzzle parent){
    copy(parent);
    this.G = parent.G +1;
}

Puzzle(int[] numbers){
    fields = new byte[16];
    for(int i = 0; i < 16 ; i++)
    {
        fields[i] = (byte) numbers[i];
    }
    countEmptyPosition();
    this.G = Integer.MAX_VALUE;
    manhattan=Manhattan.manhattanForPuzzle(this);
}

void copy(Puzzle parent){
    emptyPos = parent.emptyPos;
    emptyCol = parent.emptyCol;
    emptyRow = parent.emptyRow;
    fields = new byte[16];
    System.arraycopy(parent.fields, 0, fields, 0, 16);
    F = parent.F;
    G = parent.G;
    H = parent.H;
    manhattan=parent.manhattan;
}
void randomize()
{
    for (int i = 15; i > 0; i--)
    {
        int j = Main.random.nextInt(i+1);
        byte temp = fields[i];
        fields[i] = fields[j];
        fields[j] = temp;
    }
    //printPuzzle(false);
}

public void shuffle(){
    randomize();
    countEmptyPosition();
    Manhattan manhattan= new Manhattan();
    this.manhattan=manhattan.getHeuristicValue(this);
    //countPuzzleValue(); //?
}

private int countInversions(){
    int inversions = 0;
    for(int i=0;i<16;i++){
        for(int j=0;j<i;j++){
            if(fields[i] != 0 && fields[i] < fields[j]) inversions++;
        }
    }
    return inversions;
}

boolean isSolvable(){
    return countInversions() % 2 != emptyPos/4 %2;
}

private void countEmptyPosition(){
    for(int i = 0; i < 16 ; i++)
    {
        if(fields[i]==0){
            emptyPos = (byte) i;
            emptyRow = (byte) (emptyPos/4);
            emptyCol = (byte) (emptyPos%4);
            return;
        }
    }
    emptyPos = -1;
}

void move(int direction){
    switch (direction) {
        case 0 -> {
            manhattan-=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos+1],this),fields[emptyPos+1]-1);
            fields[emptyPos] = fields[emptyPos + 1];
            fields[emptyPos + 1] = 0;
            manhattan+=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos],this),fields[emptyPos]-1);
            emptyPos++;
            emptyCol++;
        }
        case 1 -> {
            manhattan-=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos-4],this),fields[emptyPos-4]-1);
            fields[emptyPos] = fields[emptyPos - 4];
            fields[emptyPos - 4] = 0;
            manhattan+=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos],this),fields[emptyPos]-1);
            emptyPos -= 4;
            emptyRow--;
        }
        case 2 -> {
            manhattan-=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos-1],this),fields[emptyPos-1]-1);
            fields[emptyPos] = fields[emptyPos - 1];
            fields[emptyPos - 1] = 0;
            manhattan+=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos],this),fields[emptyPos]-1);
            emptyPos--;
            emptyCol--;
        }
        case 3 -> {
            manhattan-=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos+4],this),fields[emptyPos+4]-1);
            fields[emptyPos] = fields[emptyPos + 4];
            fields[emptyPos + 4] = 0;
            manhattan+=Manhattan.manhattanDistance(Manhattan.findNumber(fields[emptyPos],this),fields[emptyPos]-1);
            emptyPos += 4;
            emptyRow++;
        }
    }
    setH(manhattan);
    //setH(Main.heuristic.getHeuristicValue(this));
    //System.out.println(manhattan+" "+getH());
}
public ArrayList<Puzzle> getNeighbors(){
    ArrayList<Puzzle> neighbors = new ArrayList<Puzzle>();
    Puzzle puzzle;
    for(int i=0;i<4;i++){
        if(checkMove(i))
        {
            puzzle = new Puzzle(this);
            puzzle.move(i);
            neighbors.add(puzzle);
        }
    }
    return neighbors;
}

public void printPuzzle(boolean includeFullData){
    Main.tablesPrinted++;
    System.out.println("__________");
    for(int i = 0; i < 16 ; i++)
    {
        if(fields[i]>9) System.out.print(fields[i]+" ");
        if(fields[i]<10) System.out.print("0"+ fields[i]+" ");
        if(i%4 == 3) System.out.println();
    }
    if(includeFullData){
        System.out.println("empty "+emptyPos);
        System.out.println("inversions "+countInversions());
        if(isSolvable())
            System.out.println("fValue "+ F);
        System.out.println("tables printed "+Main.tablesPrinted);
    }
    System.out.println("----------");
}

boolean checkMove(int direction){
    return !((emptyCol == 3 && direction == 0) || (emptyRow == 0 && direction == 1) ||
        (emptyCol == 0 && direction == 2) || (emptyRow== 3 && direction == 3));
}

}
