package org.example;

import java.util.List;
import java.util.Stack;

public class IDAStar {
    static int FOUND =0;

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

    int search_ida_star(Stack<Puzzle> path, int g, int bound){
        Main.polled++;
        Puzzle node = path.lastElement();
        if(Main.polled %100000000==0)
        {
            System.out.println("polled: "+Main.polled);
            System.out.println("g: "+g);
            //node.printPuzzle(false);
        }
        int f = g + Main.heuristic.getHeuristicValue(node,Main.endPuzzle);
        if (f > bound){
            //System.out.println("ret f: "+f);
            return f;
        }
        if (node.equals(Main.endPuzzle)){
            //System.out.println("endPuzzle");
            return FOUND;
        }
        int min = Integer.MAX_VALUE;
        List<Puzzle> nextPuzzles = node.getNeighbors();
        for (Puzzle succ : nextPuzzles) {
            if(getEqual(path,succ)==null){
                path.add(succ);
                int t=search_ida_star(path,g+1,bound);
                if (t==FOUND){
                    //System.out.println("FOUND");
                    return FOUND;
                }
                if(t<min)
                    min=t;
                path.pop();
            }
        }
        //System.out.println("min: "+min);
      return min;
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
