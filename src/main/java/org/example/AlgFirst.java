package org.example;

import java.util.*;

public class AlgFirst {
    static boolean result = false;
    static Queue<Node> queueInit = new LinkedList<>();
    static Set<char[][]> setInit = new HashSet<>();
    static int count = 0;
    static final char[][] initState = {{'4','8','1'},
            {' ','3','6'},
            {'2','7','5'}};
    static final char[][] finalState = {{'1','2','3'},
            {'8',' ','4'},
            {'7','6','5'}};

    static Node nodeInit = new Node(initState, null, 0, 1, 0);


    public static char[][] copy2DArray(char[][] from) {
        char[][] newArray = new char[3][3];
        for (int i = 0; i < 3; i++) {
            newArray[i] = Arrays.copyOf(from[i], 3);
        }
        return newArray;
    }

    public static char[][] move(Node parent, int row, int col){
        char[][] state = copy2DArray(parent.state);
        state[parent.row][parent.col] = state[row][col];
        state[row][col] = ' ';

        return state;
    }

    public static Node create(Node parent, char[][] state, int row, int col){
        return new Node(state, parent, parent.state[parent.row][parent.col], row, col);
    }

    public static boolean compare(char[][] firstState, char[][] secondState){
        boolean flag = true;

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(firstState[i][j] != secondState[i][j])
                    flag = false;

        return flag;
    }

    public static boolean searchMatches(char[][] state){
        boolean flag = false;

        Iterator<char[][]> iter = setInit.iterator();
        while (iter.hasNext()) {
            if(compare(iter.next(), state)){
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static void optionsState(Node node){

        if (node.col > 0 && !result)
            addNode(node, node.row, node.col - 1);

        if (node.col < 2 && !result)
            addNode(node, node.row, node.col + 1);

        if (node.row > 0 && !result)
            addNode(node, node.row - 1, node.col);

        if (node.row < 2 && !result)
            addNode(node, node.row + 1, node.col);
    }

    public static void addNode(Node parent, int row, int col){
        char[][] state = move(parent, row, col);

        if(compare(state, finalState)){
            nodeInit = create(parent, state, row, col);
            result = true;
        }

        if(!searchMatches(state)){
            Node newNode = create(parent, state, row, col);

            setInit.add(state);
            queueInit.add(newNode);
        }
    }

    public static void breadthFirstSearch(){
        queueInit.add(nodeInit);
        setInit.add(nodeInit.state);

        while(nodeInit != null && !result) {
            nodeInit = queueInit.poll();
            optionsState(nodeInit);
            count++;
        }

    }

    public static void printPath(){

        System.out.println("Path");
        nodeInit.printNode();
        nodeInit = nodeInit.parent;

        while(nodeInit != null){
            System.out.println("Переход");
            nodeInit.printNode();
            nodeInit = nodeInit.parent;
        }
    }
}
