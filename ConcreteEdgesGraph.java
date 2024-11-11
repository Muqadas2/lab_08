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
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a directed graph with a set of vertices and a list of edges between vertices.
    // Representation invariant:
    //   - No duplicate vertices.
    //   - No duplicate edges with the same source and target. 
    //   - All edges have non-negative weights.
    // Safety from rep exposure:
    //   - All fields are private.
    //   - Methods return copies of mutable objects to avoid external modification.
    
    // Check representation invariant
    private void checkRep() {
        for (Edge edge : edges) {
            assert edge.getWeight() >= 0;
            assert vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget());
        }
    }
    
    @Override
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");
        
        add(source);
        add(target);
        
        int previousWeight = 0;
        Edge existingEdge = null;
        
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                previousWeight = edge.getWeight();
                existingEdge = edge;
                break;
            }
        }
        
        if (weight == 0 && existingEdge != null) {
            edges.remove(existingEdge); // Remove the edge if weight is 0
        } else if (existingEdge != null) {
            edges.remove(existingEdge);
            edges.add(new Edge(source, target, weight)); // Update the edge's weight
        } else if (weight > 0) {
            edges.add(new Edge(source, target, weight)); // Add new edge with given weight
        }
        
        checkRep();
        return previousWeight;
    }
    
    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\nEdges:\n");
        for (Edge edge : edges) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Represents an immutable, weighted directed edge in the graph.
 */
class Edge {
    
    private final String source;
    private final String target;
    private final int weight;
    
    // Abstraction function:
    //   Represents a directed edge from source to target with a specified weight.
    // Representation invariant:
    //   - Weight must be non-negative.
    // Safety from rep exposure:
    //   - All fields are private and final.
    
    // Constructor
    public Edge(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");
        
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // Check representation invariant
    private void checkRep() {
        assert weight >= 0;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getTarget() {
        return target;
    }
    
    public int getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return String.format("Edge from %s to %s with weight %d", source, target, weight);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;
        
        Edge other = (Edge) obj;
        return this.source.equals(other.source) && this.target.equals(other.target) && this.weight == other.weight;
    }
    
    @Override
    public int hashCode() {
        return source.hashCode() + target.hashCode() + Integer.hashCode(weight);
    }
}
