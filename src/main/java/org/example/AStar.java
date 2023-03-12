package org.example;

import java.util.*;

public class AStar {

public Puzzle initialPuzzle,goalPuzzle;

public AStar(Puzzle initialPuzzle, Puzzle goalPuzzle) {
    this.initialPuzzle=initialPuzzle;
    this.goalPuzzle=goalPuzzle;
}

public Stack<Puzzle> solve() {
    int nodeExplored = 0;
    Stack<Puzzle> toReturn = new Stack<>();
    PriorityQueue<Puzzle> queue = new PriorityQueue<Puzzle>(10000, new PuzzleComparator());
    Set<Puzzle> visited = new HashSet<Puzzle>();

    queue.add(initialPuzzle);
    visited.add(initialPuzzle);
    while (!queue.isEmpty()) {
        nodeExplored++;
        //Updates view every 100 000 nodes explored
        if (nodeExplored % 10000 == 0){
            System.out.println("explored: "+nodeExplored);
        }
        Puzzle current = queue.poll();
        if (current.equals(goalPuzzle)) {
            System.out.println("koniec!!!");
            return toReturn;
            /*
            this.numOfSteps = current.getDepth();
            this.nodeExplored = nodeExplored;
            toReturn = current.getPath();
            return toReturn;*/
        }
        if(nodeExplored%10000 == 0)
            current.printPuzzle(true);
        List<Puzzle> nextPuzzles = current.getNeighbors();
        for (Puzzle puzzle : nextPuzzles) {
            puzzle.countPuzzleValue();
            if (Main.getEqual(visited,puzzle) == null) {
                queue.add(puzzle);
                visited.add(puzzle);
            }
        }
    }
    return toReturn;
}
}
