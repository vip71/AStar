package org.example;

import java.util.ArrayList;
import java.util.Stack;

public class Puzzle {

private int depth;

private int emptyPos;
public int[] fields;

public int fValue;

public boolean open,closed;

public void setDepth(int i){
    depth = i;
}

public int getDepth(){
    return depth;
}
public void setParent(Puzzle parent) {
    this.parent = parent;
    depth = parent.depth +1;
}

private Puzzle parent;

Puzzle(){
    fields = new int[16];
    for(int i = 0; i < 16 ; i++)
    {
        fields[i] = (i+1)%16;
    }
    countEmptyPosition();
    countPuzzleValue();
    this.depth = Integer.MAX_VALUE;
}

Puzzle(Puzzle parent){
    copy(parent);
    this.parent = parent;
    this.depth = parent.depth +1;
}

void copy(Puzzle parent){
    emptyPos = parent.emptyPos;
    fields = new int[16];
    System.arraycopy(parent.fields, 0, fields, 0, 16);
    fValue = parent.fValue;
    open = parent.open;
    closed = parent.closed;
}

boolean equals(Puzzle compared){
    for(int i=0;i<16;i++){
        if(fields[i] != compared.fields[i])
            return false;
    }
    return true;
}

void randomize()
{
    for (int i = 15; i > 0; i--)
    {
        int j = Main.random.nextInt(i+1);
        int temp = fields[i];
        fields[i] = fields[j];
        fields[j] = temp;
    }
    //printPuzzle(false);
}

public void shuffle(){
    randomize();
    countEmptyPosition();
    countPuzzleValue();
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
            emptyPos = i;
            return;
        }
    }
    emptyPos = -1;
}

public void countPuzzleValue() {
    fValue = depth + Main.heuristic.getHeuristicValue(this,Main.endPuzzle);
}

boolean move(int direction){
    if( (emptyPos % 4 == 3 && direction == 0) || (emptyPos / 4 == 0 && direction == 1) ||
        (emptyPos % 4 == 0 && direction == 2) || (emptyPos / 4 == 3 && direction == 3) ){
        fValue = Integer.MAX_VALUE;
        return false;
    }
    switch (direction) {
        case 0:
            fields[emptyPos] = fields[emptyPos + 1];
            fields[emptyPos + 1] = 0;
            emptyPos++;
            break;
        case 1:
            fields[emptyPos] = fields[emptyPos - 4];
            fields[emptyPos - 4] = 0;
            emptyPos-=4;
            break;
        case 2:
            fields[emptyPos] = fields[emptyPos - 1];
            fields[emptyPos - 1] = 0;
            emptyPos--;
            break;
        case 3:
            fields[emptyPos] = fields[emptyPos + 4];
            fields[emptyPos + 4] = 0;
            emptyPos+=4;
            break;
    }
    countEmptyPosition();
    countPuzzleValue();
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
            System.out.println("fValue "+ fValue);
        System.out.println("tables printed "+Main.tablesPrinted);
    }
    System.out.println("----------");
}

int getHeuristicValue(){
    return Main.heuristic.getHeuristicValue(this,Main.endPuzzle);
}

boolean checkMove(int direction){
    return !((emptyPos % 4 == 3 && direction == 0) || (emptyPos / 4 == 0 && direction == 1) ||
        (emptyPos % 4 == 0 && direction == 2) || (emptyPos / 4 == 3 && direction == 3));
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
