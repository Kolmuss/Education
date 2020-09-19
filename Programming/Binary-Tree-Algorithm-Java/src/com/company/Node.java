package com.company;

public class Node<T>{
    private Node<T> parent;
    private Node<T> lChild;
    private Node<T> rChild;
    private T data;

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent.getData()+
                ", lChild=" + lChild.getData() +
                ", rChild=" + rChild.getData() +
                ", data=" + data +
                '}';
    }

    public Node(Node<T> parent, Node<T> lChild, Node<T> rChild, T data) {
        this.parent = parent;
        this.lChild = lChild;
        this.rChild = rChild;
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLChild() {
        return lChild;
    }

    public void setLChild(Node<T> lChild) {
        this.lChild = lChild;
    }

    public Node<T> getRChild() {
        return rChild;
    }

    public void setRChild(Node<T> rChild) {
        this.rChild = rChild;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
