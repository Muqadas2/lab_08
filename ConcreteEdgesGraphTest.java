package graph;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    private ConcreteEdgesGraph graph;

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    @Before
    public void setUp() {
        graph = new ConcreteEdgesGraph();
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
