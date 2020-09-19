import java.util.ArrayList;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        Graph<String> g = new Graph<>(true);

        g.addCity("A", 1);
        g.addCity("B", 1);
        g.addCity("C", 1);
        g.addCity("D", 1);
        g.addCity("E", 1);
        g.addCity("F", 1);
        g.addCity("G", 1);

        g.addEdge("A", "B", 20);
        g.addEdge("A", "C", 11);
        g.addEdge("A", "E", 1);
        g.addEdge("B", "C", 8);
        g.addEdge("C", "D", 4);

        g.addEdge("C", "F", 7);
        g.addEdge("D", "E", 3);
        g.addEdge("D", "G", 51);
        g.addEdge("E", "F", 41);
        g.addEdge("F", "G", 30);


        g.getRoute("A", "G");

    }
}
