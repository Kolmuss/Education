package com.company;

public interface Binary<T> {
    void insert(T obj);
    void delete(T obj);
    Node<T> find(T obj);
    void print();
    T smallest();
    T largest();
    int size();
}
