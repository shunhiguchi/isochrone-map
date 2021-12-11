import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;

/**
 * The IsochroneMap class identifies vertices in a digraph reachable within the
 * threshold cost, and produces visualization.
 */
public class IsochroneMap {

    /* Delimiter of CSV files. */
    private final static String DELIMITER = ",";

    /* Map of vertices. */
    private HashMap<Integer, Vertex> vertices = new HashMap<>();

    /* Map of edges. */
    private HashMap<Integer, Edge> edges = new HashMap<>();

    /* Map of vertex adjacency list. */
    private HashMap<Integer, HashMap<Integer, Edge>> adj = new HashMap<>();

    public IsochroneMap() {}

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
     * Visualize the isochone map.
     */
    private void visualize() {
        // Visualize Isochrone Map
        JFrame f = new JFrame();
        f.setSize(900, 600);
        f.setTitle("Isochrone Map");

        Visualizer v = new Visualizer(this.vertices, this.edges);
        f.add(v);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    /*
     * Main method.
     */
    public static void main(String[] args) {
        int sourceVertexId = Integer.parseInt(args[2]);
        int thresholdCost = Integer.parseInt(args[3]);

        /* Create an instance of IsochroneMap with user arguments. */
        IsochroneMap im = new IsochroneMap();

        /* Parse user input files: vertices and edges. */
        im.parseVertices(args[0]);
        im.parseEdges(args[1]);

        /* Set a source vertex. */
        im.vertices.get(sourceVertexId).isSource = true;

        /* Create an adjacency list. */
        for (Vertex v: im.vertices.values()) {
            HashMap<Integer, Edge> item = new HashMap<Integer, Edge>();
            im.adj.put(v.id, item);
        }
        for (Edge e: im.edges.values()) {
            im.adj.get(e.v1.id).put(e.v2.id, e);
        }

        /* Compute the shortest paths. */
        ShortestPath sp = new ShortestPath(im.vertices, im.edges, im.adj);
        sp.dijkstra(im.vertices.get(sourceVertexId));
        im.vertices = sp.vertices;

        /* Mark vertices reachable within the threshold cost and edges used as
         * the shortest paths to those vertices.
         */
        for (Vertex v: im.vertices.values()) {
            if (v.cost < thresholdCost) {
                v.isReachable = true;
                Vertex v1;
                Vertex v2 = v;
                while (v2.id != sourceVertexId) {
                    v1 = v2.prev;
                    im.edges.get(im.adj.get(v1.id).get(v2.id).id).isShortestPath = true;
                    v2 = v1;
                }
            }
        }

        /* Visualize the isochrone map. */
        im.visualize();
    }
}
