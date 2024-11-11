package graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    private ConcreteVerticesGraph graph;

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    @Before
    public void setUp() {
        graph = new ConcreteVerticesGraph();
    }

    // Test for add(String vertex)
    @Test
    public void testAddVertex() {
        assertTrue(graph.add("A")); // Test adding a new vertex
        assertFalse(graph.add("A")); // Test adding an existing vertex
        assertTrue(graph.vertices().contains("A"));
    }

    // Test for set(String source, String target, int weight)
    @Test
    public void testSetEdge() {
        assertEquals(0, graph.set("A", "B", 5)); // Test setting a new edge
        assertEquals(5, graph.set("A", "B", 10)); // Test updating an edge's weight
        assertEquals(0, graph.set("A", "B", 0)); // Test removing an edge by setting weight to 0
    }

    // Test for remove(String vertex)
    @Test
    public void testRemoveVertex() {
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertTrue(graph.remove("A")); // Test removing an existing vertex
        assertFalse(graph.vertices().contains("A"));
        assertFalse(graph.remove("C")); // Test removing a non-existent vertex
    }

    // Test for vertices()
    @Test
    public void testVertices() {
        graph.add("A");
        graph.add("B");
        Set<String> vertices = graph.vertices();
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
        assertEquals(2, vertices.size());
    }

    // Test for sources(String target)
    @Test
    public void testSources() {
        graph.set("A", "B", 5);
        graph.set("C", "B", 7);
        Map<String, Integer> sources = graph.sources("B");
        assertEquals(2, sources.size());
        assertEquals((Integer) 5, sources.get("A"));
        assertEquals((Integer) 7, sources.get("C"));
    }

    // Test for targets(String source)
    @Test
    public void testTargets() {
        graph.set("A", "B", 5);
        graph.set("A", "C", 10);
        Map<String, Integer> targets = graph.targets("A");
        assertEquals(2, targets.size());
        assertEquals((Integer) 5, targets.get("B"));
        assertEquals((Integer) 10, targets.get("C"));
    }

    // Test for toString()
    @Test
    public void testToString() {
        graph.add("A");
        graph.set("A", "B", 5);
        assertTrue(graph.toString().contains("A"));
        assertTrue(graph.toString().contains("B"));
        assertTrue(graph.toString().contains("5"));
    }
}

/**
 * Tests for Vertex class
 */
public class VertexTest {

    private Vertex vertex;

    @Before
    public void setUp() {
        vertex = new Vertex("A");
    }

    // Test for Vertex constructor and getters
    @Test
    public void testVertexConstructor() {
        assertEquals("A", vertex.getName());
    }

    // Test for adding and updating edges
    @Test
    public void testSetEdgeWeight() {
        vertex.setEdge("B", 5);
        assertEquals(5, vertex.getEdgeWeight("B"));
        
        vertex.setEdge("B", 10); // Update weight
        assertEquals(10, vertex.getEdgeWeight("B"));
        
        vertex.setEdge("B", 0); // Remove edge by setting weight to 0
        assertEquals(0, vertex.getEdgeWeight("B"));
    }

    // Test for retrieving adjacent vertices
    @Test
    public void testGetAdjacentVertices() {
        vertex.setEdge("B", 5);
        vertex.setEdge("C", 10);
        
        Map<String, Integer> adjVertices = vertex.getAdjacentVertices();
        assertEquals(2, adjVertices.size());
        assertEquals((Integer) 5, adjVertices.get("B"));
        assertEquals((Integer) 10, adjVertices.get("C"));
    }

    // Test for Vertex toString()
    @Test
    public void testVertexToString() {
        vertex.setEdge("B", 5);
        assertTrue(vertex.toString().contains("A"));
        assertTrue(vertex.toString().contains("B"));
        assertTrue(vertex.toString().contains("5"));
    }
}
