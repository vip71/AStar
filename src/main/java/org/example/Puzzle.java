package org.example;

import java.util.ArrayList;

public class Puzzle {
private boolean traversable;
private int emptyPos;
public int[] field;
private int value;

public int h,g,f;

public boolean open,closed;

Puzzle(){
    traversable = true;
    field = new int[16];
    for(int i = 0; i < 16 ; i++)
    {
        field [i] = (i+1)%16;
    }
    countEmptyPosition();
    countPuzzleValue();
}

Puzzle(Puzzle parent){
    copy(parent);
}

Puzzle moved(Puzzle parent, int direction,ArrayList<Puzzle> open, ArrayList<Puzzle> closed){

    Puzzle newPuzzle = new Puzzle(parent);
    /*newPuzzle.move(direction);
    if(newPuzzle.value == Integer.MAX_VALUE){
        return null;
    }
    Puzzle puzzle = Main.getEqual(open,newPuzzle);
    if( puzzle != null) {
        puzzle.open = true;
        puzzle.closed = false;
        return puzzle;
    }
    puzzle = puzzle == null? Main.getEqual(closed,newPuzzle) : puzzle;
    if( puzzle != null) {
        puzzle.closed = true;
        puzzle.open = false;
        return puzzle;
    }
    puzzle = newPuzzle;
    puzzle.closed=false;
    puzzle.open=false;
    return puzzle;*/
    return newPuzzle;
}

void copy(Puzzle parent){
    traversable = parent.traversable;
    emptyPos = parent.emptyPos;
    field = new int[16];
    System.arraycopy(parent.field, 0, field, 0, 16);
    value = parent.value;
    f = parent.f;
    g = parent.g;
    h = parent.h;
    open = parent.open;
    closed = parent.closed;
}

boolean equals(Puzzle compared){
    for(int i=0;i<16;i++){
        if(field[i] != compared.field[i])
            return false;
    }
    return true;
}

void randomize()
{
    for (int i = 15; i > 0; i--)
    {
        int j = Main.random.nextInt(i+1);
        int temp = field[i];
        field[i] = field[j];
        field[j] = temp;
    }
    printPuzzle(false);
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
            if(field [i] != 0 && field [i] < field [j]) inversions++;
        }
    }
    return inversions;
}

boolean isSolvable(){
    return (countInversions() % 2 != emptyPos/4 %2 );
}

public int getValue() {
    return value;
}

private void countEmptyPosition(){
    for(int i = 0; i < 16 ; i++)
    {
        if(field [i]==0){
            emptyPos = i;
            return;
        }
    }
    emptyPos = -1;
}

public void countPuzzleValue() {
    value = Main.heuristic.getHeuristicValue(this,Main.endPuzzle);
}

boolean move(int direction){
    if( (emptyPos % 4 == 3 && direction == 0) || (emptyPos / 4 == 0 && direction == 1) ||
        (emptyPos % 4 == 0 && direction == 2) || (emptyPos / 4 == 3 && direction == 3) ){
        traversable = false;
        value = Integer.MAX_VALUE;
        return false;
    }
    switch (direction) {
        case 0:
            field[emptyPos] = field[emptyPos + 1];
            field[emptyPos + 1] = 0;
            emptyPos++;
            break;
        case 1:
            field[emptyPos] = field[emptyPos - 4];
            field[emptyPos - 4] = 0;
            emptyPos-=4;
            break;
        case 2:
            field[emptyPos] = field[emptyPos - 1];
            field[emptyPos - 1] = 0;
            emptyPos--;
            break;
        case 3:
            field[emptyPos] = field[emptyPos + 4];
            field[emptyPos + 4] = 0;
            emptyPos+=4;
            break;
    }
    countEmptyPosition();
    countPuzzleValue();
    return true;
}
/*
public ArrayList<Puzzle> getNeighbors(ArrayList<Puzzle> open,ArrayList<Puzzle> closed){
    ArrayList<Puzzle> neighbors = new ArrayList<>();
    Puzzle puzzle;
    for(int i=0;i<4;i++){
        puzzle = moved(this,i,open,closed);
        if(puzzle!=null && puzzle.getValue()!=Integer.MAX_VALUE)
        {
            neighbors.add(puzzle);
        }
    }
    return neighbors;
}*/
public ArrayList<Puzzle> getNeighbors(){
    ArrayList<Puzzle> neighbors = new ArrayList<>();
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
        if(field [i]>9) System.out.print(field[i]+" ");
        if(field [i]<10) System.out.print("0"+field[i]+" ");
        if(i%4 == 3) System.out.println();
    }
    if(includeFullData){
        System.out.println("traversable "+traversable);
        System.out.println("empty "+emptyPos);
        System.out.println("inversions "+countInversions());
        if(isSolvable())
            System.out.println("distance "+value);
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

}
