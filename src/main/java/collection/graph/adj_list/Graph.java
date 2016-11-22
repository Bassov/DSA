package optimization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Adjacency list graph
public class Graph<V, E> {

    private Map<V, Vertex> vertexMap = new HashMap<>();
    private DLinkedList<Edge> edges = new DLinkedList<>();

    public void insertVertex(V value) {
        if (!vertexMap.containsKey(value)) {
            vertexMap.put(value, new Vertex(value));
        }
    }

    public Set<V> vertices() {
        return vertexMap.keySet();
    }

    public List<Edge> edges() {
        return edges.values();
    }

    public void insertEdge(V v1, V v2, E edgeValue) {
        Vertex start = vertexMap.get(v1);
        Vertex end = vertexMap.get(v2);

        if (start != null && end != null) {
            Edge edge = new Edge(edgeValue, start, end);

            DLinkedList<Edge>.Node inEdges = edges.add(edge);
            edge.inEdges = inEdges;

            DLinkedList<Edge>.Node inStart = start.adjacencyList.add(edge);
            edge.inStart = inStart;

            DLinkedList<Edge>.Node inEnd = end.adjacencyList.add(edge);
            edge.inEnd = inEnd;
        }
    }

    public void removeVertex(V vertex) {
        Vertex v = vertexMap.get(vertex);
        DLinkedList<Edge> vEdges = v.adjacencyList;
        vEdges.forEach(this::removeEdge);
        vertexMap.remove(vertex);
    }

    private E removeEdge(Edge edge) {
        if (edge == null) {
            return null;
        }
        E ev = edge.value;
        edge.inStart.destroy();
        edge.inEnd.destroy();
        edge.inEdges.destroy();
        return ev;
    }

    public E removeEdge(V v1, V v2) {
        E ev = removeEdge(getEdge(v1, v2));
        return ev;
    }

    public int degree(V vertex) {
        return vertexMap.get(vertex).adjacencyList.size();
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
        Vertex lowest;
        lowest = st.adjacencyList.size() > en.adjacencyList.size() ? en : st;

        DLinkedList<Edge> edges = lowest.adjacencyList;
        return edges.anyMatch(e -> e.end.equals(end));
    }

    public DLinkedList<Edge> incidentEdges(V start) {
        return vertexMap.get(start).adjacencyList;
    }

    public List<V> neighbors(V vertex) {
        Vertex v = vertexMap.get(vertex);
        return v.adjacencyList.values()
                .stream()
                .map(e -> opposite(v, e).value)
                .collect(Collectors.toList());
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
        Vertex lowest = st;
        V h = to;
        if (st.adjacencyList.size() > en.adjacencyList.size()) {
            lowest = en;
            h = from;
        }
        V highest = h;
        return lowest.adjacencyList
                .findBy(e -> e.end.value.equals(highest));
    }

    public E getEdgeValue(V start, V v) {
        Edge e = getEdge(start, v);
        return e.value;
    }

    public class Vertex {
        V value;
        DLinkedList<Edge> adjacencyList = new DLinkedList<>();

        public Vertex(V value) {
            this.value = value;
        }
    }

    public class Edge {
        E value;
        Vertex start, end;
        DLinkedList<Edge>.Node inStart, inEnd;
        DLinkedList<Edge>.Node inEdges;

        public Edge(E value, Vertex v1, Vertex end) {
            this.value = value;
            this.start = v1;
            this.end = end;
        }

    }

}
