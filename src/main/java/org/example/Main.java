package org.example;

import java.util.Random;
import java.util.Stack;

public class Main {
public static Random random = new Random();

public static Heuristic heuristic = new Manhattan();

static Puzzle startPuzzle;
static Puzzle endPuzzle;

public static int tablesPrinted = 0;

public static long polled =0;
public static int count=0;

public static void main(String[] args) {
    for(int i=0;i<100;i++){
        count=0;
        //int[] start = {4,6,3,0,1,2,7,12,8,5,9,14,10,11,13,15};
        //initStartAndEnd(start);
        initStartAndEnd(null);
        //initStartAndEnd(80);
        IDAStar idaStar = new IDAStar();

        test(idaStar,new LinearConflict());
        //test(idaStar,new ZeroHeuristic());
    }
}

private static void test(IDAStar idaStar,Heuristic newHeuristic) {
    heuristic = newHeuristic;
    startPuzzle.reset(true);
    endPuzzle.reset(false);
    Stack<Puzzle> road = idaStar.ida_star(startPuzzle);
    while (!road.isEmpty()) {
        count++;
        Puzzle element = road.pop();
        //element.printPuzzle(false);
    }

    System.out.println("estimated: "+ startPuzzle.getH());
    System.out.println("count: "+ count+"\n");
    count=0;
}

private static void initStartAndEnd(int[] start) {
    if(start==null){
        startPuzzle = new Puzzle();
        startPuzzle.shuffle();
    } else {
        startPuzzle = new Puzzle(start);
    }
    endPuzzle = new Puzzle();
    System.out.println("First puzzle");
    startPuzzle.printPuzzle(false);
}

private static void initStartAndEnd(int shuffles) {
    startPuzzle= new Puzzle();
    endPuzzle = new Puzzle();
    int j,i = 0;
    while (i<shuffles){
        j=random.nextInt(4);
        if(startPuzzle.checkMove(j)){
            i++;
            startPuzzle.move(j);
        }
    }
    while(!startPuzzle.isSolvable()){
        startPuzzle.shuffle();
    }
    //System.out.println("First puzzle");
    //startPuzzle.printPuzzle(false);
}

}