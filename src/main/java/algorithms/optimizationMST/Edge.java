package algorithms.optimizationMST;

import java.util.Comparator;

public class Edge {

    Weight value;
    Vertex start, end;
    int index;

    public Edge(Weight value, Vertex v1, Vertex end) {
        this.value = value;
        this.start = v1;
        this.end = end;
    }

    public static Comparator<Edge> comparator() {
        return Comparator.comparing(e -> e.value.dist);
    }

}