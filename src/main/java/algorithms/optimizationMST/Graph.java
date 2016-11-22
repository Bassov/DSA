package algorithms.optimizationMST;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    public Map<String, Vertex> vertexMap = new HashMap<>();
    public List<Edge> edges = new ArrayList<>();

    public int size() {
        return vertexMap.size();
    }

    public boolean isEmpty() {
        return vertexMap.isEmpty();
    }

    public List<String> vertices() {
        return vertexMap.keySet()
                .stream()
                .collect(Collectors.toList());
    }
    
    public void insertVertex(String value) {
        if (!vertexMap.containsKey(value)) {
            vertexMap.put(value, new Vertex(value));
        }
    }

    public void insertEdge(String v1, String v2, Weight edgeValue) {
        Vertex start = vertexMap.get(v1);
        Vertex end = vertexMap.get(v2);

        if (start != null && end != null) {
            Edge edge = new Edge(edgeValue, start, end);
            start.adjacencyMap.put(end, edge);
            edges.add(edge);
            edge.index = edges.indexOf(edge);
        }
    }

    public Weight getEdgeValue(String from, String to) {
        Vertex st = vertexMap.get(from);
        Vertex en = vertexMap.get(to);
        return st.adjacencyMap.get(en).value;
    }

    public void removeVertex(String vertex) {
        Vertex v = vertexMap.get(vertex);
        Iterator<Edge> it = v.adjacencyMap.values().iterator();
        while (it.hasNext())
        {
            Edge item = it.next();
            it.remove();
            item.start.adjacencyMap.remove(item.end);
            item.end.adjacencyMap.remove(item.start);
        }
        vertexMap.remove(vertex);
    }

    private Weight removeEdge(Edge edge) {
        if (edge == null) {
            return null;
        }
        Weight ev = edge.value;
        edge.start.adjacencyMap.remove(edge.end);
        edge.end.adjacencyMap.remove(edge.start);
        edges.remove(edge.index);
        return ev;
    }

    public Weight removeEdge(String v1, String v2) {
        Weight ev = removeEdge(getEdge(v1, v2));
        return ev;
    }

    public int degree(String vertex) {
        return vertexMap.get(vertex).adjacencyMap.size();
    }

    public String opposite(String v, Edge e) {
        return e.start.value.equals(v) ? e.end.value : e.start.value;
    }

    public boolean areAdjacent(String start, String end) {
        Vertex st = vertexMap.get(start);
        Vertex en = vertexMap.get(end);

        return st.adjacencyMap.containsKey(en);
    }

    public List<Edge> incidentEdges(String start) {
        return vertexMap.get(start).adjacencyMap.values()
                .stream()
                .collect(Collectors.toList());
    }

    public List<String> neighbors(String vertex) {
        return vertexMap.get(vertex)
                .adjacencyMap
                .keySet()
                .stream()
                .map(v -> v.value)
                .collect(Collectors.toList());
    }

    public void setEdgeBoth(String from, String to, Weight weight) {
        insertEdge(from, to, weight);
        insertEdge(to, from, weight);
    }

    public Weight setEdge(String from, String to, Weight weight) {
        Edge edge = getEdge(from, to);
        if (edge == null) {
            return null;
        }
        Weight ew = edge.value;
        edge.value = weight;
        return ew;
    }

    private Edge getEdge(String from, String to) {
        Vertex st = vertexMap.get(from);
        Vertex en = vertexMap.get(to);
        return st.adjacencyMap.get(en);
    }

}
