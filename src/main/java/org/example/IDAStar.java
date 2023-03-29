package org.example;

import java.util.List;
import java.util.Stack;

public class IDAStar {
    static final int FOUND =0;
    static final int DISTANCE_TO_PARENT=1;

    public Stack<Puzzle> ida_star(Puzzle root){
        Main.polled=0;
        int bound = Main.heuristic.getHeuristicValue(root,Main.endPuzzle);
        Stack<Puzzle> path = new Stack<>();
        path.push(root);
        int t;
        while (true){
            t = search_ida_star(path,0,bound);
            if(t==FOUND){
                //System.out.println("ending ida");
                System.out.println("polled: "+Main.polled);
             return path;
            }
            if(t==Integer.MAX_VALUE){
                System.out.println("error");
                return null;
            }
            bound = t;
        }
    }

    int search_ida_star(Stack<Puzzle> path, int graphCost, int currentFBound){
        Puzzle current = path.lastElement();
        debug(graphCost);
        current.setH(Main.heuristic.getHeuristicValue(current,Main.endPuzzle));
        current.setG(graphCost);
        current.setF(graphCost + current.getH());
        if (current.getF() > currentFBound){
            //System.out.println("ret f: "+f);
            return current.getF();
        }
        if (current.equals(Main.endPuzzle)){
            //System.out.println("endPuzzle");
            return FOUND;
        }
        int minFFound = Integer.MAX_VALUE;
        List<Puzzle> nextPuzzles = current.getNeighbors();
        for (Puzzle child : nextPuzzles) {
            if(getEqual(path,child)==null){
                path.add(child);
                int minFOverBound=search_ida_star(path,current.getG()+DISTANCE_TO_PARENT,currentFBound);
                if (minFOverBound==FOUND){
                    //System.out.println("FOUND");
                    return FOUND;
                }
                if(minFOverBound<minFFound)
                    minFFound=minFOverBound;
                path.pop();
            }
        }
        //System.out.println("min: "+min);
      return minFFound;
    }

private static void debug(int graphCost) {
    Main.polled++;
    if(Main.polled %100000000==0)
    {
        System.out.println("polled: "+Main.polled);
        System.out.println("graphCost: "+ graphCost);
        //current.printPuzzle(false);
    }
}

public static Puzzle getEqual(Stack<Puzzle> puzzles, Puzzle specificPuzzle){
        for (Puzzle puzzle: puzzles) {
            if(puzzle.equals(specificPuzzle)){
                return puzzle;
            }
        }
        return null;
    }
}
