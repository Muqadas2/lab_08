/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    @Test
    public void testConstructorAndGraph() throws IOException {
        File corpus = new File("testCorpus.txt");
        try (PrintWriter writer = new PrintWriter(corpus)) {
            writer.println("Hello world. Hello again, world.");
        }
        
        GraphPoet poet = new GraphPoet(corpus);
        assertEquals(2, poet.graph.targets("hello").size());
        assertTrue(poet.graph.targets("hello").containsKey("world"));
    }

    @Test
    public void testPoemWithBridgeWord() throws IOException {
        File corpus = new File("testCorpus.txt");
        try (PrintWriter writer = new PrintWriter(corpus)) {
            writer.println("This is a test of the system.");
        }
        
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Test the system.";
        String expected = "Test of the system.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testPoemNoBridgeWord() throws IOException {
        File corpus = new File("testCorpus.txt");
        try (PrintWriter writer = new PrintWriter(corpus)) {
            writer.println("An unrelated corpus with no relevant data.");
        }
        
        GraphPoet poet = new GraphPoet(corpus);
        String input = "Random words here.";
        String expected = "Random words here.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("testCorpus.txt");
        try (PrintWriter writer = new PrintWriter(corpus)) {
            writer.println("This corpus won't matter.");
        }
        
        GraphPoet poet = new GraphPoet(corpus);
        assertEquals("", poet.poem(""));
    }

    @Test
    public void testToString() throws IOException {
        File corpus = new File("testCorpus.txt");
        try (PrintWriter writer = new PrintWriter(corpus)) {
            writer.println("Simple test corpus.");
        }
        
        GraphPoet poet = new GraphPoet(corpus);
        assertTrue(poet.toString().contains("GraphPoet with graph:"));
    }

    
}
