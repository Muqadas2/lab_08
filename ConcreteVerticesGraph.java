package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Each Vertex in the vertices list represents a node in the graph.
    // Representation invariant:
    //   Each vertex is unique, and edges have non-negative weights.
    // Safety from rep exposure:
    //   The vertices list is private, and access is through defensive copying.

    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    // Checks the representation invariant
    private void checkRep() {
        Set<String> vertexNames = new HashSet<>();
        for (Vertex vertex : vertices) {
            assert !vertexNames.contains(vertex.getName());
            vertexNames.add(vertex.getName());
        }
    }
    
    @Override 
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getName().equals(vertex)) {
                return false; // Vertex already exists
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override 
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = null;
        Vertex targetVertex = null;
        
        for (Vertex vertex : vertices) {
            if (vertex.getName().equals(source)) {
                sourceVertex = vertex;
            }
            if (vertex.getName().equals(target)) {
                targetVertex = vertex;
            }
        }

        // Add vertices if they do not exist
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }

        // Set edge weight and return previous weight
        int previousWeight = sourceVertex.getEdgeWeight(target);
        sourceVertex.setEdge(target, weight);
        checkRep();
        return previousWeight;
    }
    
    @Override 
    public boolean remove(String vertex) {
        Vertex toRemove = null;
        
        // Remove vertex from the list
        for (Vertex v : vertices) {
            if (v.getName().equals(vertex)) {
                toRemove = v;
            } else {
                v.setEdge(vertex, 0); // Remove edges to the vertex
            }
        }
        
        if (toRemove != null) {
            vertices.remove(toRemove);
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override 
    public Set<String> vertices() {
        Set<String> vertexNames = new HashSet<>();
        for (Vertex vertex : vertices) {
            vertexNames.add(vertex.getName());
        }
        return vertexNames;
    }
    
    @Override 
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex vertex : vertices) {
            int weight = vertex.getEdgeWeight(target);
            if (weight > 0) {
                sources.put(vertex.getName(), weight);
            }
        }
        return sources;
    }
    
    @Override 
    public Map<String, Integer> targets(String source) {
        for (Vertex vertex : vertices) {
            if (vertex.getName().equals(source)) {
                return vertex.getAdjacentVertices();
            }
        }
        return new HashMap<>();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : vertices) {
            sb.append(vertex.toString()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Represents a vertex in the graph with edges to other vertices.
 */
class Vertex {
    
    private final String name;
    private final Map<String, Integer> edges = new HashMap<>();
    
    // Abstraction function:
    //   The name represents the vertex, and edges map represents the directed edges with weights.
    // Representation invariant:
    //   Edge weights are non-negative.
    // Safety from rep exposure:
    //   The fields are private and final where possible. The map is defensively copied when accessed externally.
    
    public Vertex(String name) {
        this.name = name;
        checkRep();
    }
    
    private void checkRep() {
        for (int weight : edges.values()) {
            assert weight >= 0;
        }
    }
    
    public String getName() {
        return name;
    }
    
    public int getEdgeWeight(String target) {
        return edges.getOrDefault(target, 0);
    }
    
    public void setEdge(String target, int weight) {
        if (weight == 0) {
            edges.remove(target);
        } else {
            edges.put(target, weight);
        }
        checkRep();
    }
    
    public Map<String, Integer> getAdjacentVertices() {
        return new HashMap<>(edges);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + " -> ");
        for (Map.Entry<String, Integer> entry : edges.entrySet()) {
            sb.append(entry.getKey()).append(" (").append(entry.getValue()).append("), ");
        }
        if (edges.size() > 0) sb.setLength(sb.length() - 2); // Remove trailing comma
        return sb.toString();
    }
}
