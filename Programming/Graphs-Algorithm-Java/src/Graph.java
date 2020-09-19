import java.util.*;

public class Graph<String> {

    private HashMap<String, ArrayList<Edge>> adjList;
    private boolean undirected;


    public Graph(boolean undirected) {
        this.adjList = new HashMap<>();
        this.undirected = undirected;
    }

    public void addCity(String val, int v) {
        adjList.putIfAbsent(val, new ArrayList<Edge>());
    }

    public void addEdge(String c1, String c2, int weight){
        String city1 = findC(c1);
        String city2 = findC(c2);
        Edge w1 = new Edge(city2, weight);
        adjList.get(city1).add(w1);
        if(undirected){
            Edge w2 = new Edge(city1, weight);
            adjList.get(city2).add(w2);
        }
    }

    private String findC(String city){
        for (String c:
                adjList.keySet()) {
            if(city.equals(c)){
                return c;
            }
        }
        return null;
    }

    public void getRoute(String origin, String destination){
        HashMap<String, Route> routes = new HashMap<>();
        ArrayList<String> visited = new ArrayList<>();
        Stack<String> st = new Stack<>();

        for (String city:
             adjList.keySet()) {
            routes.putIfAbsent(city, new Route(city));
            if (city.equals(origin)){
                routes.get(city).setDistance(-1);
            }
        }

        int count = 0;
        String next = origin;
        while (true){
            String minR = null;
            int min = Integer.MAX_VALUE;

            for (Edge route:
                 adjList.get(next)) {
                Route temp = routes.get(route.getDestination());
                int tot = route.getWeight()+count;
                if(temp.getDistance() > count+route.getWeight()){
                    temp.setDistance(count+route.getWeight());
                    temp.setOrigin(next);
                }
                if(!visited.contains(route.getDestination())) {
                    if (min > route.getWeight()) {
                        min = route.getWeight();
                        minR = route.getDestination();
                    }
                }
            }
            if(minR == null){
                break;
            }
            visited.add(next);
            next = minR;
            count += min;
        }
        next = destination;
        st.push(destination);
        while (true){
            Route temp = routes.get(next);
            st.push(temp.getOrigin());

            if(temp.getOrigin().equals(origin) || temp.getOrigin() == null){
                String s;
                while (!st.empty()){
                    s = st.pop();
                    System.out.println(s);
                }
                break;
            }
            next = temp.getOrigin();
        }
    }

    private class Edge{
        private String destination;
        private int weight;


        public Edge(String origin, int weight) {
            this.destination = origin;
            this.weight = weight;
        }

        public String getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public java.lang.String toString() {
            return "Edge{" +
                    "origin=" + destination +
                    ", weight=" + weight +
                    '}';
        }
    }


    private class Route{
        private String origin;
        private String destination;
        private int distance;

        public Route(String destination) {
            this.distance = Integer.MAX_VALUE;
            this.origin = null;
            this.destination = destination;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public java.lang.String toString() {
            return "Route{" +
                    "origin=" + origin +
                    ", destination=" + destination +
                    ", distance=" + distance +
                    '}';
        }
    }
}
