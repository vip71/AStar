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

public boolean open,closed;

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

public void reset(){
    setF(0);
    setH(0);
    setG(0);
}

public void setParent(Puzzle parent) {
    this.parent = parent;
    G = parent.G +1;
}

private Puzzle parent;

Puzzle(){
    fields = new byte[16];
    for(int i = 0; i < 16 ; i++)
    {
        fields[i] = (byte) ((i+1)%16);
    }
    countEmptyPosition();
    countPuzzleValue();
    this.G = Integer.MAX_VALUE;
    emptyRow=3;
    emptyCol=3;
    manhattan=0;
}

Puzzle(Puzzle parent){
    copy(parent);
    this.parent = parent;
    this.G = parent.G +1;
}

Puzzle(int[] numbers){
    fields = new byte[16];
    for(int i = 0; i < 16 ; i++)
    {
        fields[i] = (byte) numbers[i];
    }
    countEmptyPosition();
    countPuzzleValue();
    this.G = Integer.MAX_VALUE;
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

boolean equals(Puzzle compared){
    return Arrays.equals(fields,compared.fields);
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
    this.manhattan=manhattan.getHeuristicValue(this,Main.endPuzzle);
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

public void countPuzzleValue() {
    F = G + Main.heuristic.getHeuristicValue(this,Main.endPuzzle);
}

boolean move(int direction){
    if( !checkMove(direction)){
        F = Integer.MAX_VALUE;
        return false;
    }
    switch (direction) {
        case 0:
            fields[emptyPos] = fields[emptyPos + 1];
            fields[emptyPos + 1] = 0;
            emptyPos++;
            emptyCol++;
            break;
        case 1:
            fields[emptyPos] = fields[emptyPos - 4];
            fields[emptyPos - 4] = 0;
            emptyPos-=4;
            emptyRow--;
            break;
        case 2:
            fields[emptyPos] = fields[emptyPos - 1];
            fields[emptyPos - 1] = 0;
            emptyPos--;
            emptyCol--;
            break;
        case 3:
            fields[emptyPos] = fields[emptyPos + 4];
            fields[emptyPos + 4] = 0;
            emptyPos+=4;
            emptyRow++;
            break;
    }
    setH(Main.heuristic.getHeuristicValue(this,Main.endPuzzle));
    //countEmptyPosition();
    //countPuzzleValue();
    return true;
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

int getHeuristicValue(){
    return H;
}

boolean checkMove(int direction){
    return !((emptyCol == 3 && direction == 0) || (emptyRow == 0 && direction == 1) ||
        (emptyCol == 0 && direction == 2) || (emptyRow== 3 && direction == 3));
}

public Stack<Puzzle> getPath() {
    Puzzle current = this;
    Stack<Puzzle> path = new Stack<Puzzle>();
    while (current != null) {
        path.push(current);
        current = current.getParent();
    }
    return path;
}

public Puzzle getParent() {
    return parent;
}

}
