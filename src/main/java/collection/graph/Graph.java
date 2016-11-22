package collection.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Graph<V, E> {

    private Map<V, Vertex> vertexMap = new HashMap<>();

    public int size() {
        return vertexMap.size();
    }

    public boolean isEmpty() {
        return vertexMap.isEmpty();
    }

    public List<V> vertices() {
        return vertexMap.keySet()
                .stream()
                .collect(Collectors.toList());
    }
    
    public void insertVertex(V value) {
        if (!vertexMap.containsKey(value)) {
            vertexMap.put(value, new Vertex(value));
        }
    }

    public void insertEdge(V v1, V v2, E edgeValue) {
        Vertex start = vertexMap.get(v1);
        Vertex end = vertexMap.get(v2);

        if (start != null && end != null) {
            Edge edge = new Edge(edgeValue, start, end);
            start.adjacencyMap.put(end, edge);
        }
    }

    public E getEdgeValue(V from, V to) {
        Vertex st = vertexMap.get(from);
        Vertex en = vertexMap.get(to);
        return st.adjacencyMap.get(en).value;
    }

    public void removeVertex(V vertex) {
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

    private E removeEdge(Edge edge) {
        if (edge == null) {
            return null;
        }
        E ev = edge.value;
        edge.start.adjacencyMap.remove(edge.end);
        edge.end.adjacencyMap.remove(edge.start);
        return ev;
    }

    public E removeEdge(V v1, V v2) {
        E ev = removeEdge(getEdge(v1, v2));
        return ev;
    }

    public int degree(V vertex) {
        return vertexMap.get(vertex).adjacencyMap.size();
    }

    public Vertex[] endVertices(Edge edge) {
        Vertex[] result = (Vertex[])new Object[2];
        result[1] = edge.start;
        result[2] = edge.end;
        return result;
    }

    public Vertex opposite(Vertex v, Edge e) {
        return e.start.equals(v) ? e.end : e.start;
    }

    public boolean areAdjacent(V start, V end) {
        Vertex st = vertexMap.get(start);
        Vertex en = vertexMap.get(end);

        return st.adjacencyMap.containsKey(en);
    }

    public Collection<Edge> incidentEdges(V start) {
        return vertexMap.get(start).adjacencyMap.values();
    }

    public Collection<Vertex> neighbors(V vertex) {
        return vertexMap.get(vertex).adjacencyMap.keySet();
    }

    public void setEdgeBoth(V from, V to, E weight) {
        insertEdge(from, to, weight);
        insertEdge(to, from, weight);
    }

    public E setEdge(V from, V to, E weight) {
        Edge edge = getEdge(from, to);
        if (edge == null) {
            return null;
        }
        E ew = edge.value;
        edge.value = weight;
        return ew;
    }

    private Edge getEdge(V from, V to) {
        Vertex st = vertexMap.get(from);
        Vertex en = vertexMap.get(to);
        return st.adjacencyMap.get(en);
    }

    public class Vertex {
        public V value;
        Map<Vertex, Edge> adjacencyMap = new HashMap<>();

        public Vertex(V value) {
            this.value = value;
        }
    }

    public class Edge {
        E value;
        Vertex start, end;

        public Edge(E value, Vertex v1, Vertex end) {
            this.value = value;
            this.start = v1;
            this.end = end;
        }

    }

}
