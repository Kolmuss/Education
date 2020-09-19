package com.company;


public class CTree {
    private Node head;
    private double equal;

    private Node split(char[]e, int last, int first){
        int ind = last;
        boolean b = false;
        for (int i = last; i >= first; i--) {
            if (e[i] == '*' || e[i] == '/') {
                ind = i;
                b=true;
                break;
            }
        }

        for (int i = last; i >= first; i--) {
            if (e[i] == '-' || e[i] == '+') {
                ind = i;
                b=true;
                break;
            }
        }

        if(b) {
            Node r = split(e, last, ind + 1);
            Node l = split(e, ind - 1, first);
            return new Node(r, l, e[ind]);
        }
        else {
            return new Node(e[ind]);
        }
    }

    public CTree(String e) {
        equal = 0;
        head = split(e.toCharArray(), e.length()-1, 0);
    }

    private double calculate(Node node){
        if(node.getLChild()==null || node.getRChild()==null)
            return node.getData()-48;
        double l = calculate(node.getLChild());
        double r = calculate(node.getRChild());
        switch (node.getData()){
            case '+':
                return l+r;
            case '-':
                return l-r;
            case '*':
                return l*r;
            case '/':
                return l/r;
            default:
                return node.getData()-48;
        }
    }

    public Node getHead(){
        return head;
    }

    public double equals(){
        equal = calculate(head);
        return equal;
    }

    private class Node{
        Node rChild;
        Node lChild;
        char data;

        public Node(Node rChild, Node lChild, char data) {
            this.rChild = rChild;
            this.lChild = lChild;
            this.data = data;
        }
        public Node(char data) {
            this.data = data;
        }

        public Node getRChild() {
            return rChild;
        }

        public Node getLChild() {
            return lChild;
        }

        public char getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}