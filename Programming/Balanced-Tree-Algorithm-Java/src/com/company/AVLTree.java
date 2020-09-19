package com.company;

public class AVLTree <T extends Comparable>{
    Node root;
    int h;

    int height(Node N) {
        if (N == null)
            return 0;

        if(N.height>h)
            h=N.height;

        return N.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    void insert(T key){
        root = step(root, key);
    }

    Node step(Node node, T key) {

        if (node == null)
            return (new Node(key));

        if (key.compareTo(node.key) == -1)
            node.left = step(node.left, key);
        else if (key.compareTo(node.key) == 1)
            node.right = step(node.right, key);
        else
            return node;

        node.height = 1 + max(height(node.left),
                height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key.compareTo(node.left.key) == -1)
            return rightRotate(node);

        if (balance < -1 && key.compareTo(node.right.key) == 1)
            return leftRotate(node);

        if (balance > 1 && key.compareTo(node.left.key) == 1) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) == -1) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void delete(T obj) {
        Node cur = find(obj);
        if(cur==root){
            Node temp = root.right;
            if(temp!=null) {
                while (temp.left != null) {
                    temp = temp.left;
                }
                temp.left = root.left;
                root = temp;
            }
            if(cur.left!=null)
                cur.left.parent = temp;
            return;
        }
        boolean b = false;

        if(cur.parent.key.compareTo(cur.key)>0)
            b=true;

        if(cur.right!=null){
            if(b)
                cur.parent.left = cur.right;
            else
                cur.parent.right = cur.right;

            Node temp = cur.right;
            while(temp.left !=null){
                temp = temp.left;
            }
            temp.left = cur.left;
            if(cur.left!=null)
                cur.left.parent=temp;
            return;
        }else
        if(cur.left!=null){
            if(b)
                cur.parent.left = cur.left;
            else
                cur.parent.right = cur.left;
            return;
        }

        if(b)
            cur.parent.left = null;
        else
            cur.parent.right = null;

    }

    void levelOrder() {
        for (int i = h; i > 0; i--) {
            pRec(root, i);
            System.out.println("____");
        }
    }

    private Node findRec(Node node, T obj){
        switch (node.key.compareTo(obj)){
            case 0:
                return node;
            case 1:
                if(node.left == null)
                    return null;
                return findRec(node.left, obj);
            case -1:
                if(node.right == null)
                    return null;
                return findRec(node.right, obj);
        }
        return null;
    }

    public Node find(T obj) {
        return findRec(root, obj);
    }


    private void pRec(Node node, int level){
        if(node.left!=null)
            pRec(node.left, level);

        if(node.height==level)
            System.out.println(node.key);

        if(node.right!=null)
            pRec(node.right, level);
    }

    private class Node{
        int height;
        T key;
        Node left, right, parent;

        Node(T d) {
            key = d;
            height = 1;
        }
    }

}
