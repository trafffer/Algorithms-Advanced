package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//Kruskal algorithm
public class ExamPrep2 {
    public static List<Edge>[] graph;
    public static boolean[] visited;
    public static Map<Integer, List<Integer>> forest = new HashMap<>();
    public static int[] damage;
    public static int max = Integer.MIN_VALUE;

    public static class Edge implements Comparable<Edge> {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return Integer.compare(this.weight, edge.weight);
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());
        int hits = Integer.parseInt(reader.readLine());

        visited = new boolean[nodes];
        graph = new ArrayList[nodes];
        damage = new int[nodes];

        for (int i = 0; i < edges; i++){
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int dest = tokens[1];
            int weight = tokens[2];

            Edge edge = new Edge(source, dest, weight);

            if (graph[source]==null){
                graph[source] = new ArrayList<>();
            }

            if (graph[dest]==null){
                graph[dest] = new ArrayList<>();
            }

            graph[source].add(edge);
            graph[dest].add(edge);
        }

        for (int i = 0; i < nodes; i++){
            msf(i);
        }

        for (int i = 0; i < hits; i++){
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int node = tokens[0];
            int power = tokens[1];

            damageNodes(node, node, power);
        }

        System.out.println(max);
    }

//    Iteracia za prilagane na damage-a
    private static void damageNodes(int node, int next, int power) {
        if (power < 1){
            return;
        }

        damage[node] += power;

        if (max < damage[node]){
            max = damage[node];
        }

        if (forest.get(node)!= null) {
            for (int child : forest.get(node)) {
                if (child != next) {
                    damageNodes(child, node, power / 2);
                }
            }
        }
    }

//Minimal spanning forest - nai-malka pokrivasha gora
    private static void msf(int node) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        
        visitNode(node, queue);

        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            int from = edge.source;
            int to = edge.destination;

            if (visited[from] && visited[to]){
                continue;
            }

            forest.putIfAbsent(from, new ArrayList<>());
            forest.putIfAbsent(to,new ArrayList<>());

            forest.get(from).add(to);
            forest.get(to).add(from);

            if (!visited[from]){
                visitNode(from, queue);
            } else if(!visited[to]){
                visitNode(to, queue);
            }
        }
    }

    private static void visitNode(int node, PriorityQueue<Edge> queue) {
        visited[node] = true;

        if (graph[node] != null) {
            for (Edge edge : graph[node]) {
                int nextNode = node == edge.source ? edge.destination : edge.source;

                if (!visited[nextNode]) {
                    queue.offer(edge);
                }
            }
        }
    }
}
