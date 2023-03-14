package org.example;

import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class Main {
public static Random random = new Random();

public static Heuristic heuristic = new LinearConflict();

static Puzzle startPuzzle;
static Puzzle endPuzzle;

public static int tablesPrinted = 0;

public static void main(String[] args) {

        initStartAndEnd();
        AStar aStar = new AStar(startPuzzle, endPuzzle);
        Stack<Puzzle> path = aStar.solve();
        int pathElements = 0;
        while (!path.isEmpty()) {
            Puzzle element = path.pop();
            pathElements++;
            element.printPuzzle(false);
        }
        System.out.println("polled: " + aStar.polled);
        System.out.println("path: " + pathElements);
}

private static void initStartAndEnd() {
    endPuzzle = new Puzzle();
    startPuzzle = new Puzzle();
    //startPuzzle.shuffle();
    int i=0;
    int j;
    while (i<20){
        j=random.nextInt(4);
        if(startPuzzle.checkMove(j)){
            i++;
            startPuzzle.move(j);
        }
    }
    /*startPuzzle.move(2);
    startPuzzle.move(2);
    startPuzzle.move(1);
    startPuzzle.move(1);
    startPuzzle.move(0);
    startPuzzle.move(1);
    startPuzzle.move(2);
    startPuzzle.move(3);
    startPuzzle.move(2);
    startPuzzle.move(1);
    startPuzzle.move(0);
    startPuzzle.move(3);*/
    startPuzzle.setDepth(0);
    while(!startPuzzle.isSolvable()){
        startPuzzle.shuffle();
    }
    System.out.println("First puzzle");
    startPuzzle.printPuzzle(false);
}

public static Puzzle getEqual(Set<Puzzle> puzzles, Puzzle specificPuzzle){
    for (Puzzle puzzle: puzzles) {
        if(puzzle.equals(specificPuzzle)){
            return puzzle;
        }
    }
    return null;
}

}