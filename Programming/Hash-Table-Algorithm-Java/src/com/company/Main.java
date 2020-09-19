package com.company;

public class Main {


    public static void main(String[] args) {
	    MyHashMap map = new MyHashMap();

	    map.put("Anton", "Krasauchik");
        map.put("Mark", "Tigr");
        map.put("Yura", "Voje");
        map.remove("Mark");
        map.put("Sam", "GOOOSE");
        map.put("John", "Krag");
        map.put("Deen", "Rak");


//        map.print();

        System.out.println(map.keySet());

        System.out.println(map.valuesSet());


    }
}
