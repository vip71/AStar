package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Main {
public static Random random = new Random();

public static Heuristic heuristic = new BadHeuristic();

static Puzzle startPuzzle;
static Puzzle endPuzzle;

public static int tablesPrinted = 0;
public static int tablesSearched = 0;

public static void main(String[] args) {

    initStartAndEnd();
    ArrayList<Puzzle> open = new ArrayList<>();
    open.add(startPuzzle);
    ArrayList<Puzzle> closed = new ArrayList<>();
    Puzzle puzzle;
    int puzzleCost;
    int temp;

    AStar aStar = new AStar(startPuzzle,endPuzzle);
    aStar.solve();
    /*
    while(open.size()>0){
        puzzle = null;
        puzzleCost = Integer.MAX_VALUE;
        for (Puzzle testPuzzle: open) {
            temp = heuristic.getHeuristicValue(startPuzzle,testPuzzle)+heuristic.getHeuristicValue(testPuzzle,endPuzzle);
            if (temp< puzzleCost){
                puzzle = testPuzzle;
                puzzleCost = temp;
            }
        }

        if(puzzle.equals(endPuzzle)){
            System.out.println("Koniec");
            puzzle.printPuzzle(true);
            return;
        }

        tablesSearched++;
        if(tablesSearched%10000==0){
            puzzle.printPuzzle(false);
            System.out.println("open: "+open.size()+" closed:"+closed.size());
        }

        open.remove(puzzle);
        closed.add(puzzle);
        puzzle.open=false;
        puzzle.closed=true;
        for (Puzzle nextPuzzle: puzzle.getNeighbors(open,closed)) {
            if(!nextPuzzle.open && !nextPuzzle.closed){
                open.add(nextPuzzle);
                nextPuzzle.h = heuristic.getHeuristicValue(nextPuzzle,endPuzzle);
                nextPuzzle.g = puzzle.g+heuristic.getHeuristicValue(puzzle,nextPuzzle);
                nextPuzzle.f = nextPuzzle.g + nextPuzzle.h;
            }
            else{
                if (nextPuzzle.g > puzzle.g + heuristic.getHeuristicValue(puzzle,nextPuzzle)){
                    nextPuzzle.g = puzzle.g + heuristic.getHeuristicValue(puzzle,nextPuzzle);
                    nextPuzzle.f = nextPuzzle.g + nextPuzzle.h;
                    if(!nextPuzzle.closed){
                        open.add(nextPuzzle);
                        puzzle.closed=false;
                        puzzle.open=true;
                        closed.remove(nextPuzzle);
                    }
                }
            }
        }
    }*/
}

private static void initStartAndEnd() {
    endPuzzle = new Puzzle();
    startPuzzle = new Puzzle();
    startPuzzle.shuffle();
    while(!startPuzzle.isSolvable()){
        startPuzzle.shuffle();
    }
    startPuzzle.printPuzzle(true);
    startPuzzle.g = 0;
    startPuzzle.h = 0;
    startPuzzle.f = startPuzzle.g + startPuzzle.h;
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