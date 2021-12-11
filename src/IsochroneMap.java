import javax.swing.JComponent;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final String DELIMITER = ",";

/**
 * The IsochroneMap class identifies vertices in a digraph reachable within the
 * threshold cost, and produces visualization.
 *
 * Usage: java IsochroneMap vertices.csv edges.csv source threshold
 * E.g., java IsochroneMap vertices.csv edges.csv 0 7
 */
public class IsochroneMap {

    /* Delimiter of CSV files. */
    private final static String DELIMITER = ",";

    /* Map of vertices. */
    private HashMap<Integer, Vertex> vertices;

    /* Map of edges. */
    private HashMap<Integer, Edge> edges;

    /* Map of vertex adjacency list. */
    private HashMap<Integer, List<Vertex>> adj;

    public IsochroneMap() {



        // Determine if each edge is part of the shortest paths
        for (int i = 0; i < this.prev.length; i++) {
            if (!vertices[i].reachable) continue;
            int fromNode;
            int toNode = i;
            while (toNode != sourceVertexId) {
                fromNode = prev[toNode];
                String fromToNodePair = Integer.toString(fromNode) + "-" + Integer.toString(toNode);
                edges[edgesFromVertices.get(fromToNodePair)].used = true;
                toNode = fromNode;
            }
        }

        // Visualize Isochrone Map
        JFrame f = new JFrame();
        f.setSize(900, 600);
        f.setTitle("Isochrone Map");

        Visualizer v = new Visualizer(vertices, edges);
        f.add(v);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    /*
     * Parse vertices from the input file.
     */
    private void parseVertices(String verticesFilePath) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(verticesFilePath))) {
            String line = br.readLine(); // Skip the header row.
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(IsochroneMap.DELIMITER);
                int id = Integer.parseInt(columns[0]);
                int x = Integer.parseInt(columns[1]);
                int y = Integer.parseInt(columns[2]);
                this.vertices.put(id, new Vertex(id, x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Parse edges from the input file.
     */
    private void parseEdges(String edgesFilePath) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(edgesFilePath))) {
            String line = br.readLine(); // Skip the header row.
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                int id = Integer.parseInt(columns[0]);
                int v1id = Integer.parseInt(columns[1]);
                int v2id = Integer.parseInt(columns[2]);
                int cost = Integer.parseInt(columns[3]);
                this.edges.put(id, new Edge(id, vertices.get(v1id), vertices.get(v2id), cost));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Main method.
     */
    public static void main(String[] args) {

        /* Create an instance of IsochroneMap with user arguments. */
        IsochroneMap im = new IsochroneMap();

        /* Parse user input files: vertices and edges. */
        im.parseVertices(args[0]);
        im.parseEdges(args[1]);

        /* Set a source vertex. */
        im.vertices.get(Integer.parseInt(args[2])).isSource = true;

        /* Create an adjacency list. */
        for (Vertex v: im.vertices.values()) {
            List<Vertex> item = new ArrayList<>();
            im.adj.put(v.id, item);
        }
        for (Edge e: im.edges.values()) {
            im.adj.get(e.v1.id).add(e.v2)
        }

        /* Compute the shortest paths. */
        ShortestPath sp = new ShortestPath(V);
    }
}
