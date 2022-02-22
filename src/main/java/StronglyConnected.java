package main.java;

import java.util.*;
import java.util.stream.Collectors;

public class StronglyConnected {
    public static int[][] graph;
    public static int[] distances;
    public static int[] prev;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int edges = Integer.parseInt(scanner.nextLine());

        graph = new int[nodes+1][nodes+1];

        for (int i = 0; i < edges; i++ ){
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

        try{
            bellmanFord(source);
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
            return;
        }

        List<Integer> path = new ArrayList<>();
        path.add(destination);

        int node = prev[destination];

        while(node != -1){
           path.add(node);
           node = prev[node];
        }

        Collections.reverse(path);
        System.out.println(path.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.println(distances[destination]);
    }

    private static void bellmanFord(int source) {
        distances = new int[graph.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        prev = new int[graph.length];
        Arrays.fill(prev, -1);

        distances[source] = 0;

        for (int i = 0; i < graph.length - 1; i++){
            for (int r = 0; r < graph.length; r++){
                for (int c = 0; c < graph[r].length; c++){
                    int weight = graph[r][c];
                    if (weight != 0 ){
                        if (distances[r]!= Integer.MAX_VALUE){
                            int newValue = distances[r] + weight;
                            if (newValue < distances[c]){
                                distances[c] = newValue;
                                prev[c] = r;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < graph.length - 1; i++){
            for (int r = 0; r < graph.length; r++){
                for (int c = 0; c < graph[r].length; c++){
                    int weight = graph[r][c];
                    if (weight != 0 ){
                        if (distances[r]!= Integer.MAX_VALUE){
                            int newValue = distances[r] + weight;
                            if (newValue < distances[c]){
                                throw new IllegalStateException("Negative Cycle Detected");
                            }
                        }
                    }
                }
            }
        }
        System.out.println();
    }
}
