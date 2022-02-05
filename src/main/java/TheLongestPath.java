package main.java;

import java.util.*;

public class TheLongestPath {
    public static int[][] graph;
    public static int[] distances;
    public static boolean[] visited;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int edges = Integer.parseInt(scanner.nextLine());

        graph = new int[nodes + 1][nodes + 1];

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

            int source = tokens[0];
            int dest = tokens[1];
            int weight = tokens[2];

            graph[source][dest] = weight;
        }
        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        distances = new int[graph.length];
        Arrays.fill(distances, Integer.MIN_VALUE);
        visited = new boolean[graph.length];

        distances[source] = 0;
        ArrayDeque<Integer> sorted = new ArrayDeque<>();

        for (int i = 0; i < graph.length; i++){
            topologicalSort(i, sorted);
        }

        while(!sorted.isEmpty()){
            int node = sorted.pop();
            for (int i=0; i< graph[node].length; i++){
                int weight = graph[node][i];
                if (weight != 0){
                    if (distances[node] + weight > distances[i] ){
                        distances[i] = distances[node] + weight;
                    }
                }
            }
        }
        System.out.println(distances[destination]);
    }

    private static void topologicalSort(int i, ArrayDeque<Integer> sorted) {
        if (visited[i]){
            return;
        }
        visited[i] = true;
        for (int j=0; j < graph[i].length; j++){
            if (graph[i][j] != 0 ){
                topologicalSort(j, sorted);
            }
        }
        sorted.push(i);
    }
}