package algorithms.shortest_path_dijkstra;

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

    private Graph<String, Weight> graph = new Graph<>();

    public LHD(String graphPath) {
        this.graph = buildFromFile(graphPath);
    }

    private String findShortestPath(String v1, String v2, String kilos) {
        double k = Double.parseDouble(kilos);
        // --------------DIJKSTRA INITIALIZATION-------------------------
        MinPriorityQueue<Double, String> pq = new MinPriorityQueue<>();
        Map<String, Double> distances = new HashMap<>();
        Map<String, Double> cost = new HashMap<>();

        pq.put(0.0, v1);
        distances.put(v1, 0.0);
        // --------------DIJKSTRA INITIALIZATION-------------------------
        while (!pq.isEmpty()) {
            String start = pq.pop();
            graph.neighbors(start).forEach(vertex -> {
                String v = vertex.value;
                Weight ev = graph.getEdgeValue(start, v);
                double distV = distances.get(v) == null ? 0 : distances.get(v);
                double distStart = distances.get(start);
                double costU = cost.get(start) == null ? 0 : cost.get(start);
                if (distV == 0 || distV > distStart + ev.time) {
                    double newDist = distStart + ev.time;
                    double newCost = costU + ev.cost;
                    distances.put(v, newDist);
                    cost.put(v, newCost);
                    pq.put(newDist, v);
                }
            });
        }

        double time = distances.get(v2);
        double totalCost = k * cost.get(v2);
        return String.format("%s %s %.1f %.1f %.1f", v1, v2, k, time, totalCost);
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

    private Graph<String, Weight> buildFromFile(String path) {
        Path p = Paths.get(path);
        Graph<String, Weight> result = new Graph<>();
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
