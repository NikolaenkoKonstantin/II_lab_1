package org.example;

import java.util.*;

public class AlgSecond {
    static boolean result = false;
    static Queue<Node> queueInit = new LinkedList<>();
    static Queue<Node> queueFinal = new LinkedList<>();
    static Set<char[][]> setInit = new HashSet<>();
    static Set<char[][]> setFinal = new HashSet<>();
    static int count = 0;
    static final char[][] initState = {{'4','8','1'},
            {' ','3','6'},
            {'2','7','5'}};
    static final char[][] finalState = {{'1','2','3'},
            {'8',' ','4'},
            {'7','6','5'}};
    static Node nodeInit = new Node(initState, null, 0, 1, 0);
    static Node nodeFinal = new Node(finalState, null, 0, 1, 1);
    static Node nodePoint;

    static char[][] statePoint;


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

    public static boolean searchMatches(char[][] state, Set<char[][]> set){
        boolean flag = false;

        Iterator<char[][]> iter = set.iterator();
        while (iter.hasNext()) {
            if(compare(iter.next(), state)){
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static Node addNode(Node parent, int row, int col, char[][] state){
        return create(parent, state, row, col);
    }

    public static Node searchPoint(Queue<Node> queue, char[][] state){
       Node matchNode = null;

        Iterator<Node> iter = queue.iterator();
        while (iter.hasNext()) {
            Node tempNode = iter.next();
            if(compare(tempNode.state, state)){
                matchNode = tempNode;
                break;
            }
        }

        return matchNode;
    }

    public static void addInitNode(Node parent, int row, int col){
        char[][] state = move(parent, row, col);

        if( searchMatches(state, setFinal)) {
            result = true;
            statePoint = state;
            nodePoint = addNode(parent, row, col, state);
            System.out.println("INIT");
        }

        if (!searchMatches(state, setInit)){
            setInit.add(state);
            queueInit.add(addNode(parent, row, col, state));
        }
    }

    public static void addFinalNode(Node parent, int row, int col){
        char[][] state = move(parent, row, col);

        if(!result && (compare(state, initState) || searchMatches(state, setInit))) {
            result = true;
            statePoint = state;
            nodePoint = addNode(parent, row, col, state);
            System.out.println("FINAL");
        }

        if (!searchMatches(state, setFinal)){
            setFinal.add(state);
            queueFinal.add(addNode(parent, row, col, state));
        }
    }

    public static void optionsStateFinal(){

        if (nodeFinal.col > 0 && !result)
            addFinalNode(nodeFinal, nodeFinal.row, nodeFinal.col - 1);

        if (nodeFinal.col < 2 && !result)
            addFinalNode(nodeFinal, nodeFinal.row, nodeFinal.col + 1);

        if (nodeFinal.row > 0 && !result)
            addFinalNode(nodeFinal, nodeFinal.row - 1, nodeFinal.col);

        if (nodeFinal.row < 2 && !result)
            addFinalNode(nodeFinal, nodeFinal.row + 1, nodeFinal.col);
    }


    public static void optionsStateInit(){

        if (nodeInit.col > 0 && !result)
            addInitNode(nodeInit, nodeInit.row, nodeInit.col - 1);

        if (nodeInit.col < 2 && !result)
            addInitNode(nodeInit, nodeInit.row, nodeInit.col + 1);

        if (nodeInit.row > 0 && !result)
            addInitNode(nodeInit, nodeInit.row - 1, nodeInit.col);

        if (nodeInit.row < 2 && !result)
            addInitNode(nodeInit, nodeInit.row + 1, nodeInit.col);
    }

    public static void algBidirectional(){
        nodeInit = queueInit.poll();
        nodeFinal = queueFinal.poll();

        optionsStateInit();
        optionsStateFinal();
        count++;
    }


    public static void bidirectionalSearch(){
        queueInit.add(nodeInit);
        setInit.add(nodeInit.state);

        queueFinal.add(nodeFinal);
        setFinal.add(nodeFinal.state);

        while(!queueInit.isEmpty() && !queueFinal.isEmpty() && !result){
            algBidirectional();
        }
    }

    public static void printPath(){
        List<Node> path = createPath();

        System.out.println("Path");

        path.forEach(w -> {
            w.printNode();
            System.out.println("Переход");
        });
    }

    public static Node searchStateNode(Queue<Node> queue){
        Node matchNode = null;

        Iterator<Node> iter = queue.iterator();
        while (iter.hasNext()) {
            Node tempNode = iter.next();
            if(compare(tempNode.state, statePoint)){
                matchNode = tempNode;
                break;
            }
        }

        return matchNode;
    }

    public static List<Node> createPath(){
        List<Node> path = new ArrayList<>();
        Node tempInit = searchStateNode(queueInit);
        Node tempFinal = searchStateNode(queueFinal);

        reverse(path, tempFinal.parent);

        while(tempInit != null){
            path.add(tempInit);
            tempInit = tempInit.parent;
        }

        return path;
    }

    public static void reverse(List<Node> path, Node node){
        if(node != null) {
            reverse(path, node.parent);
            path.add(node);
        }
    }

}
