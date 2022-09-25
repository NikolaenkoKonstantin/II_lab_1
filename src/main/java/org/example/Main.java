package org.example;

import java.util.*;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static boolean result = false;
    static int count = 0;

    public static void first(){
        AlgFirst.breadthFirstSearch();

        result = AlgFirst.result;
        int size = AlgFirst.setInit.size();
        count = AlgFirst.count;

        AlgFirst.printPath();

        System.out.println("\n\nSIZE = " + size);
        System.out.println("Количество циклов = " + count);
    }

    public static void second(){
        AlgSecond.bidirectionalSearch();

        result = AlgSecond.result;
        int size = AlgSecond.setInit.size() + AlgSecond.setFinal.size();
        count = AlgSecond.count;

        AlgSecond.printPath();

        System.out.println("\n\nSIZE = " + size);
        System.out.println("Количество циклов = " + count);
    }

    public static void main(String[] args) {
        System.out.println("1 - в ширину\n2 - двусторонний\nВведите:");
        int kod = scanner.nextInt();

        if(kod == 1)
            first();
        else
            second();

        if(result == true)
            System.out.println("\nWIN!!!\n");
        else
            System.out.println("\nLOSE!!!\n");
    }
}