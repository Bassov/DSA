package algorithms.optimizationMST.most_optimal_solution;

import algorithms.optimizationMST.Graph;
import algorithms.optimizationMST.MinPriorityQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LHD {

    private Graph graph = new Graph();

    public LHD(String graphPath) {
        this.graph = buildFromFile(graphPath);
    }

    private String findShortestPath(String v1, String v2, String kilos) {
        Graph graph = optimiseGraph(v1);
        double k = Double.parseDouble(kilos);
        // --------------DIJKSTRA INITIALIZATION-------------------------
        MinPriorityQueue<Double, String> pq = new MinPriorityQueue<>();
        Map<String, Double> distances = new HashMap<>();
        Map<String, Double> cost = new HashMap<>();
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> paths = new HashMap<>();

        pq.put(0.0, v1);
        distances.put(v1, 0.0);
        // --------------DIJKSTRA INITIALIZATION-------------------------
        while (!pq.isEmpty()) {
            String start = pq.pop();
            graph.neighbors(start).forEach(v -> {
                Weight ev = graph.getEdgeValue(start, v);
                double distV = distances.get(v) == null ? 0 : distances.get(v);
                double distStart = distances.get(start);
                double costU = cost.get(start) == null ? 0 : cost.get(start);
                double distStartKm = dist.get(start) == null ? 0 : dist.get(start);
                String path = paths.get(start) == null ? start : paths.get(start);
                if (distV == 0 || distV > distStart + ev.time) {
                    double newDist = distStart + ev.time;
                    double newCost = costU + ev.cost;
                    double newDistKm = distStartKm + ev.dist;
                    String newPath = path + ", " + v;
                    distances.put(v, newDist);
                    cost.put(v, newCost);
                    dist.put(v, newDistKm);
                    paths.put(v, newPath);
                    pq.put(newDist, v);
                }
            });
        }

        double time = distances.get(v2);
        double totalCost = k * cost.get(v2);
        double d = dist.get(v2);
        String p = paths.get(v2);
        return String.format("%s %s %.1f %.1f %.1f %.1f %s", v1, v2, k, time, totalCost, d, p);
    }

    private Graph optimiseGraph(String vertex) {
        Graph result = new Graph();
        graph.vertices().forEach(result::insertVertex);
        // --------------DIJKSTRA INITIALIZATION-------------------------
        MinPriorityQueue<Double, String> pq = new MinPriorityQueue<>();
        Map<String, Double> distances = new HashMap<>();

        pq.put(0.0, vertex);
        distances.put(vertex, 0.0);
        // --------------DIJKSTRA INITIALIZATION-------------------------
        while (!pq.isEmpty()) {
            String start = pq.pop();
            graph.neighbors(start).forEach(v -> {
                Weight ev = graph.getEdgeValue(start, v);
                double distV = distances.get(v) == null ? 0 : distances.get(v);
                double distStart = distances.get(start);
                if (distV == 0 || distV > distStart + ev.dist) {
                    double newDist = distStart + ev.dist;
                    distances.put(v, newDist);
                    result.setEdgeBoth(start, v, ev);
                    pq.put(newDist, v);
                }
            });
        }
        return result;
    }

    public void calculateFromFile(String input, String output) throws IOException {
        Path in = Paths.get(input);
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        Files.lines(in)
                .forEach(l -> {
                    String[] task = l.split(" ");
                    writer.println(findShortestPath(task[0], task[1], task[2]));
                });
        writer.close();
    }

    private Graph buildFromFile(String path) {
        Path p = Paths.get(path);
        Graph result = new Graph();
        try {
            BufferedReader reader = Files.newBufferedReader(p);
            String vertices = reader.readLine();
            String[] weights = reader.readLine().split(" ");

            Arrays.stream(vertices.split(" ")).forEach(result::insertVertex);
            for (int i = 0; i < weights.length; i += 3) {
                result.setEdgeBoth(weights[i], weights[i + 1], new Weight(weights[i + 2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private class Weight {

        double dist;
        double time;
        double cost;

        public Weight(String params) {
            String[] p = params.split(":");
            this.dist = Double.parseDouble(p[0]);
            this.time = Double.parseDouble(p[1]);
            this.cost = Double.parseDouble(p[2]);
        }

    }

}
