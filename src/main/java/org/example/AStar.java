package org.example;

import java.util.*;

public class AStar {

public Puzzle initialPuzzle,goalPuzzle;

int polled = 0;
int reopened = 0;

public AStar(Puzzle initialPuzzle, Puzzle goalPuzzle) {
    this.initialPuzzle=initialPuzzle;
    this.goalPuzzle=goalPuzzle;
}

public Stack<Puzzle> solve() {
    Stack<Puzzle> toReturn = new Stack<>();
    PriorityQueue<Puzzle> opened = new PriorityQueue<>(10000, new PuzzleComparator());
    Set<Puzzle> closed = new HashSet<>();

    opened.add(initialPuzzle);
    closed.add(initialPuzzle);
    while (!opened.isEmpty()) {
        polled++;
        Puzzle current = getSmallestOpened(opened);
        opened.remove(current);
        closed.add(current);
        if (current.equals(goalPuzzle)) {
            System.out.println("koniec!!!");
            //printOpened(opened);
            printData(closed,opened);
            toReturn = current.getPath();
            return toReturn;
        }
        if(polled%10000 == 0){
            printData(closed,opened);
        }
        List<Puzzle> nextPuzzles = current.getNeighbors();
        for (Puzzle puzzle : nextPuzzles) {
            puzzle.countPuzzleValue();
            if (Main.getEqual(closed,puzzle) == null) {
                opened.add(puzzle);
                //closed.add(puzzle);
            }
            else if(Main.getEqual(closed,puzzle).getDepth() > current.getDepth() + 1){
                Puzzle temp = Main.getEqual(closed,puzzle);
                reopened++;
                //System.out.println("parent change "+Main.getEqual(closed,puzzle).getDistance()+" "+(current.getDistance()+1));
                temp.setParent(current);
                temp.setDepth(current.getDepth() + 1);
                temp.countPuzzleValue();
                closed.remove(temp);
                opened.remove(temp);
                opened.add(temp);
            }
        }
    }
    return toReturn;
}

private static void printOpened(PriorityQueue<Puzzle> opened) {
    Puzzle temp = getSmallestOpened(opened);
    while (temp!=null)
    {
        temp.printPuzzle(true);
        opened.remove(temp);
        temp= getSmallestOpened(opened);
    }
}

private static Puzzle getSmallestOpened(PriorityQueue<Puzzle> opened) {
    Puzzle smallest = null;
    for (Puzzle puzzle: opened) {
        if( (smallest == null ? Integer.MAX_VALUE : smallest.fValue) > puzzle.fValue)
        {
            smallest = puzzle;
        }
    }
    return smallest;
}

private void printData(Set<Puzzle> closed,PriorityQueue<Puzzle> opened) {
    System.out.println();
    System.out.println("polled: "+polled);
    System.out.println("reopened: "+ reopened);
    System.out.println("closed: "+(closed.size()));
    System.out.println("opened: "+(opened.size()));
    System.out.println("smallest f: "+getSmallestOpened(opened).fValue);
    if((Main.getEqual(closed,Main.endPuzzle)!=null)){
        System.out.println("final found");
    }
}
}
