/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   Represents a directed graph where nodes are words in the corpus and edges denote adjacency with weights as frequency.
    // Representation invariant:
    //   Graph must not have null vertices or edges.
    // Safety from rep exposure:
    //   The graph field is private and final, and only immutable operations are exposed.

    /**
     * Create a new poet with the graph from corpus.
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> words = List.of(Files.readString(corpus.toPath()).toLowerCase().split("\\s+"));
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i);
            String word2 = words.get(i + 1);
            graph.set(word1, word2, graph.targets(word1).getOrDefault(word2, 0) + 1);
        }
        checkRep();
    }

    private void checkRep() {
        for (String vertex : graph.vertices()) {
            assert vertex != null : "Null vertex in graph";
            for (Map.Entry<String, Integer> edge : graph.targets(vertex).entrySet()) {
                assert edge.getKey() != null : "Null edge target in graph";
                assert edge.getValue() > 0 : "Edge weight must be positive";
            }
        }
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        List<String> words = List.of(input.split("\\s+"));
        StringBuilder poem = new StringBuilder();
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i).toLowerCase();
            String word2 = words.get(i + 1).toLowerCase();

            // Find the bridge word
            String bridge = null;
            int maxWeight = 0;
            for (String candidate : graph.targets(word1).keySet()) {
                if (graph.targets(candidate).containsKey(word2)) {
                    int weight = graph.targets(word1).get(candidate) + graph.targets(candidate).get(word2);
                    if (weight > maxWeight) {
                        maxWeight = weight;
                        bridge = candidate;
                    }
                }
            }

            poem.append(words.get(i)).append(" ");
            if (bridge != null) {
                poem.append(bridge).append(" ");
            }
        }
        poem.append(words.get(words.size() - 1));
        return poem.toString();
    }

    @Override
    public String toString() {
        return "GraphPoet{" + "graph=" + graph + '}';
    }
}



