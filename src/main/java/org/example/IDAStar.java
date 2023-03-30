package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class IDAStar {
static final int FOUND = -1;
static final int DISTANCE_TO_PARENT = 1;

public Stack<Puzzle> ida_star(Puzzle root) {
    Main.polled = 0;
    int bound = Main.heuristic.getHeuristicValue(root);
    Stack<Puzzle> path = new Stack<>();
    HashSet<byte[]> pathHash = new HashSet<>();
    path.push(root);
    pathHash.add(root.fields);
    int t;
    while (true) {
        t = search_ida_star(path, pathHash, 0, bound);
        if (t == FOUND) {
            System.out.println("polled: " + Main.polled);
            return path;
        }
        if (t == Integer.MAX_VALUE) {
            System.out.println("error");
            return null;
        }
        bound = t;
    }
}

int search_ida_star(Stack<Puzzle> path, HashSet<byte[]> pathHash, int graphCost, int currentFBound) {
    Puzzle current = path.lastElement();
    current.setG(graphCost);
    current.setF(graphCost + current.getH());
    Main.polled++;
    debug(graphCost);
    if (current.getF() > currentFBound) {
        return current.getF();
    }
    if(Arrays.equals(Main.endPuzzle.fields, current.fields)){
        return FOUND;
    }
    int minFFound = Integer.MAX_VALUE;
    List<Puzzle> nextPuzzles = current.getNeighbors();
    for (Puzzle child : nextPuzzles) {
        if (!pathHash.contains(child.fields)) {
            path.add(child);
            pathHash.add(child.fields);
            int minFOverBound = search_ida_star(path, pathHash, current.getG() + DISTANCE_TO_PARENT, currentFBound);
            if (minFOverBound == FOUND) {
                return FOUND;
            }
            if (minFOverBound < minFFound)
                minFFound = minFOverBound;
            pathHash.remove(path.pop().fields);
        }
    }
    return minFFound;
}

private static void debug(int graphCost) {
    if (Main.polled % 10000000 == 0) {
        System.out.println("polled: " + Main.polled +" graphCost: " + graphCost);
        //current.printPuzzle(false);
    }
}
}
