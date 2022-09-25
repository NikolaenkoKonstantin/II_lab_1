package org.example;

public class Node {
    char[][] state;
    Node parent;
    int action;
    int depth;
    int row;
    int col;

    Node(char[][] state, Node parent, int action, int row, int col){
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = parent != null ? parent.depth + 1 : 1;
        this.row = row;
        this.col = col;
    }

    public void printNode() {
        System.out.print("\tГлубина: ");
        System.out.print(depth+"\n");
        for (int i = 0; i < 3; i++) {
            System.out.print("\t\t");
            for (int j = 0; j < 3; j++)
                System.out.print(state[i][j]+" ");
            System.out.println("");
        }
        System.out.println("");
    }

}
