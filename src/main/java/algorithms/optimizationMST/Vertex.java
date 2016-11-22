package algorithms.optimizationMST;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    String value;
    Map<Vertex, Edge> adjacencyMap = new HashMap<>();

    public Vertex(String value) {
        this.value = value;
    }

}