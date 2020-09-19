package com.company;

public class Main {

    public static void main(String[] args) {

        AVLTree t = new AVLTree();

        t.insert(10);
        t.insert(20);
        t.insert(30);
        t.insert(40);
        t.insert(50);
        t.insert(25);

        t.levelOrder();
    }
}
//30 20 10 25 40 50
