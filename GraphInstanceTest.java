// Testing strategy
    //   TODO
    //   Partition for graph.add(label)
    //      graph: empty, contains multiple vertices
    //      label: exists in graph, doesn't exist in graph
    //      output: if add() returns true, graph is modified
    //              ie number of vertices increases by 1
    //              else graph unmodified
    //      observe with vertices()
    //
    //   Partition for graph.remove(label)
    //      graph: empty, contains multiple vertices
    //      label: exists in graph, doesn't exist in graph,
    //             exists in edges, doesnt exist in an edge
    //      output: if remove() returns true, graph is modified
    //              ie number of vertices decreases by 1
    //              else graph unmodified
    //      observe with vertices(), sources(), targets()
    //   
    //   Partition for graph.set(source,target,weight) -> previousWeight
    //      graph: empty, contains multiple vertices    
    //      source: exists in graph, doesn't exist in graph
    //      target: exists in graph, doesn't exist in graph
    //      No edge exists from source to target,
    //      An edge exists from source to target,
    //      weight: 0, > 0
    //      observe with sources(), targets(), vertices()
    //    
    //   Partition for graph.vertices() -> allVertices
    //      graph: empty, contains multiple vertices
    //   
    //   Partition for graph.sources(target) -> targetSources
    //      graph: empty, contains multiple vertices
    //      target: doesn't exist in graph, exists in graph,
    //              has no sources, has multiple sources
    //      targetSources contains all source vertices to target
    //      
    //   Partition for graph.targets(source) -> sourceTargets
    //      graph: empty, contains multiple vertices
    //      source: doesn't exist in graph, exists in graph,
    //             has no targets, has multiple targets
    //      sourceTargets contains all target vertices from source
    //



package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public abstract class GraphInstanceTest {
    
    private Graph<String> graph;

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Before
    public void setUp() {
        graph = emptyInstance();
    }

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), graph.vertices());
    }

    // Test adding a vertex
    @Test
    public void testAddVertex() {
        assertTrue("expected add to return true for new vertex", graph.add("A"));
        assertFalse("expected add to return false for existing vertex", graph.add("A"));
        assertEquals("expected one vertex", Set.of("A"), graph.vertices());
    }

    // Test adding edges and setting weights
    @Test
    public void testSetEdge() {
        graph.add("A");
        graph.add("B");
        assertEquals("expected new edge weight to be 0", 0, graph.set("A", "B", 5));
        assertEquals("expected updated edge weight to be 5", 5, graph.set("A", "B", 10));
        assertEquals("expected removed edge weight to be 10", 10, graph.set("A", "B", 0));
        assertFalse("expected no edge between A and B after removal", graph.targets("A").containsKey("B"));
    }

    // Test removing a vertex
    @Test
    public void testRemoveVertex() {
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        assertTrue("expected remove to return true for existing vertex", graph.remove("A"));
        assertEquals("expected remaining vertices to be only B", Set.of("B"), graph.vertices());
        
        assertFalse("expected remove to return false for non-existent vertex", graph.remove("A"));
    }

    // Test retrieving vertices
    @Test
    public void testVertices() {
        graph.add("A");
        graph.add("B");
        assertEquals("expected two vertices", Set.of("A", "B"), graph.vertices());
    }

    // Test retrieving sources of a target
    @Test
    public void testSources() {
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("C", "B", 7);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals("expected sources to contain A and C", Set.of("A", "C"), sources.keySet());
        assertEquals("expected weight of edge from A to B to be 5", (Integer) 5, sources.get("A"));
        assertEquals("expected weight of edge from C to B to be 7", (Integer) 7, sources.get("C"));
    }

    // Test retrieving targets of a source
    @Test
    public void testTargets() {
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("A", "C", 10);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals("expected targets to contain B and C", Set.of("B", "C"), targets.keySet());
        assertEquals("expected weight of edge from A to B to be 5", (Integer) 5, targets.get("B"));
        assertEquals("expected weight of edge from A to C to be 10", (Integer) 10, targets.get("C"));
    }
}
