package com.company;

public class BTree <T extends Comparable> implements Binary<T>{
    private Node<T> head;
    private int n;

    public BTree() {
        head = new Node<>(null, null,null,null);
        n=0;
    }

    private void step(Node<T> node, T obj){
        switch (node.getData().compareTo(obj)){
            case 0:
                return;
            case 1:
                if(node.getLChild() == null){
                    Node<T> temp = new Node<>(node, null, null, obj);
                    node.setLChild(temp);
                    return;
                }
                step(node.getLChild(), obj);
                break;
            case -1:
                if(node.getRChild() == null){
                    Node<T> temp = new Node<>(node, null, null, obj);
                    node.setRChild(temp);
                    return;
                }
                step(node.getRChild(), obj);
                break;
        }
    }

    @Override
    public void insert(T obj) {
        if(n==0)
            head.setData(obj);
        else
            step(head, obj);
        n++;
    }

    @Override
    public void delete(T obj) {
        Node<T> cur = find(obj);
        n--;
        if(cur==head){
            Node<T> temp = head.getRChild();
            if(temp!=null) {
                while (temp.getLChild() != null) {
                    temp = temp.getLChild();
                }
                temp.setLChild(head.getLChild());
                head = temp;
            }
            if(cur.getLChild()!=null)
                cur.getLChild().setParent(temp);
            return;
        }
        boolean b = false;

        if(cur.getParent().getData().compareTo(cur.getData())>0)
            b=true;

        if(cur.getRChild()!=null){
            if(b)
                cur.getParent().setLChild(cur.getRChild());
            else
                cur.getParent().setRChild(cur.getRChild());

            Node<T> temp = cur.getRChild();
            while(temp.getLChild()!=null){
                temp = temp.getLChild();
            }
            temp.setLChild(cur.getLChild());
            if(cur.getLChild()!=null)
                cur.getLChild().setParent(temp);
            return;
        }else
        if(cur.getLChild()!=null){
            if(b)
                cur.getParent().setLChild(cur.getLChild());
            else
                cur.getParent().setRChild(cur.getLChild());
            return;
        }

        if(b)
            cur.getParent().setLChild(null);
        else
            cur.getParent().setRChild(null);

    }

    private Node<T> findRec(Node<T> node, T obj){
        switch (node.getData().compareTo(obj)){
            case 0:
                return node;
            case 1:
                if(node.getLChild() == null)
                    return null;
                return findRec(node.getLChild(), obj);
            case -1:
                if(node.getRChild() == null)
                    return null;
                return findRec(node.getRChild(), obj);
        }
        return null;
    }

    @Override
    public Node<T> find(T obj) {
        return findRec(head, obj);
    }

    private void pRec(Node<T> node){
        if(node.getLChild()!=null)
            pRec(node.getLChild());

        System.out.println(node.getData());

        if(node.getRChild()!=null)
            pRec(node.getRChild());
    }

    @Override
    public void print() {
        pRec(head);
    }

    @Override
    public T smallest() {
        Node<T>temp=head;
        while(temp.getLChild()!=null){
            temp = temp.getLChild();
        }
        return temp.getData();
    }

    @Override
    public T largest() {
        Node<T>temp=head;
        while(temp.getRChild()!=null){
            temp = temp.getRChild();
        }
        return temp.getData();
    }

    @Override
    public int size() {
        return n;
    }
}
