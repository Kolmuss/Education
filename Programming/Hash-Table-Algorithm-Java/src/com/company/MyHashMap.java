package com.company;

import java.util.HashSet;
import java.util.Set;

public class MyHashMap <K, V>  {
    private Entry[] arr;
    private double loadFactor;
    private int len;
    private int n;

    public boolean isEmpty(){
        return len==0;
    }

    public Set<K> keySet(){
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if(arr[i]!=null){
                Entry temp = arr[i];
                while (temp!=null){
                    keys.add((K) temp.getKey());
                    temp = temp.getNext();
                }
            }
        }
        return keys;
    }

    public Set<V> valuesSet(){
        Set<V> vals = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if(arr[i]!=null){
                Entry temp = arr[i];
                while (temp!=null){
                    vals.add((V) temp.getValue());
                    temp = temp.getNext();
                }
            }
        }
        return vals;
    }

    public void print(){
        for (int i = 0; i < n; i++) {
            String str = "Cell " + i;
            Entry temp = arr[i];
            while (temp!=null){
                str += " " + temp;
                temp = temp.getNext();
            }
            System.out.println(str);
        }
    }

    public V get(K key){
        int ind = key.hashCode()%n;
        if(arr[ind]!=null){
            Entry temp = arr[ind];
            while (temp!=null){
                if(temp.getKey().equals(key))
                    return (V) temp.getValue();
                temp=temp.getNext();
            }
        }
        return null;
    }

    public void remove(K key){
        if(get(key)!=null){
            len--;
            int ind = key.hashCode()%n;
            if(arr[ind].getKey().equals(key)){
                if (arr[ind].getNext()!=null){
                    arr[ind] = arr[ind].getNext();
                }else {
                    arr[ind]=null;
                }
                return;
            }
            Entry prev = arr[ind];
            Entry temp = prev.getNext();
            while (temp!=null){
                if(temp.getKey().equals(key)) {
                    if(temp.getNext()!=null)
                        prev.setNext(temp.getNext());
                    else
                        prev.setNext(null);
                }
                prev = temp;
                temp = temp.getNext();
            }
        }
    }

    public void put(K key, V value){
        if(get(key)==null) {
            int ind = key.hashCode()%n;
            len++;
            if (arr[ind] != null) {
                int i = doubleHash(key);
                if (i == -1) {
                    arr[ind].setNext(new Entry(key, value));
                    return;
                }
                ind = i;
            }
            arr[ind] = new Entry(key, value);

            rehash((double) len / n);
        }
    }

    public MyHashMap() {
        this.arr = new Entry[10];
        this.len = 0;
        this.loadFactor = 0.75;
        this.n = 5;
    }

    private void rehash(double load){
        if(load >= loadFactor){
            Entry[] newArr = new Entry[n+5];
            for (int i = 0; i < n; i++) {
                Entry tot = arr[i];
                while (tot != null) {
                    Entry temp = tot.getNext();
                    tot.setNext(null);
                    setHash(tot, newArr);
                    tot = temp;
                }
            }
            arr = newArr;
            n+=5;
        }

    }

    private int doubleHash(K key) {
        int ind = key.hashCode()%n;
        int count = 1;
        Entry tot = arr[ind];
        while (tot != null) {
            if (count >= (1.0 / (1.0 - loadFactor))) {
                for (int i = 0; i < n; i++) {
                    ind = (key.hashCode() + i * (n - i)) % n;
                    if (arr[ind] == null)
                        return ind;
                }
            }
            count++;
            tot = tot.getNext();
        }
        return -1;
    }

    private void setHash(Entry element, Entry[] mas){
        int hash = element.getKey().hashCode();
        int size = n+5;
        int ind = hash % size;
        int count = 1;
        Entry temp = mas[ind];
        while (temp != null) {
            if (count >= (1.0 / (1.0 - loadFactor))) {
                for (int i = 0; i < size; i++) {
                    ind = (hash + i * (size - i)) % size;
                    if (mas[ind] == null){
                        mas[ind] = element;
                        return;
                    }
                }
            }
            if(temp.getNext()==null){
                temp.setNext(element);
                return;
            }
            count++;
            temp = temp.getNext();
        }
        mas[ind] = element;
    }

    private class Entry <K, V>{
        private K key;
        private V value;
        private Entry next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + key +", " + value + "]";
        }
    }
}
