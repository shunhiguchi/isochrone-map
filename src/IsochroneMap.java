import javax.swing.JComponent;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Identify nodes accessible within a threshold distance.
 * Usage: IsochroneMap vertices.csv edges.csv 0 10
 */
public class IsochroneMap {

    private int V;
    private int E;
    private int[] dist;
    private int[] prev;
    private int sourceVertexId;
    private int thresholdDist;

    public IsochroneMap(String filePathVertices, String filePathEdges,
                        int sourceVertexId, int thresholdDist) throws IOException {
        // Number of vertices
        this.V = getV(filePathVertices);

        // Source vertex ID
        this.sourceVertexId = sourceVertexId;

        // Load vertices and edges from input files
        List<List<Node>> adj = new ArrayList<List<Node>>();
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        this.E = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePathEdges))) {
            String DELIMITER = ",";
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                int fromVertex = Integer.parseInt(columns[1]);
                int toVertex = Integer.parseInt(columns[2]);
                int cost = Integer.parseInt(columns[3]);
                adj.get(fromVertex).add(new Node(toVertex, cost));
                E++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Compute the shortest paths
        ShortestPath sp = new ShortestPath(V);
        List<int[]> temp = sp.dijkstra(adj, this.sourceVertexId);
        this.dist = temp.get(0);
        this.prev = temp.get(1);

        // Create vertices
        Vertex[] vertices = new Vertex[this.V];
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePathVertices))) {
            String DELIMITER = ",";
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                int id = Integer.parseInt(columns[0]);
                int x = Integer.parseInt(columns[1]);
                int y = Integer.parseInt(columns[2]);
                boolean reachable = (dist[id] <= thresholdDist);
                boolean source = (id == sourceVertexId);
                vertices[id] = new Vertex(x, y, id, reachable, source);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create edges
        Edge[] edges = new Edge[this.E];
        Map<String, Integer> edgesFromVertices = new HashMap<String, Integer>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePathEdges))) {
            String DELIMITER = ",";
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                int id = Integer.parseInt(columns[0]);
                int fromNode = Integer.parseInt(columns[1]);
                int toNode = Integer.parseInt(columns[2]);
                int x1 = vertices[fromNode].x;
                int y1 = vertices[fromNode].y;
                int x2 = vertices[toNode].x;
                int y2 = vertices[toNode].y;
                String fromToNodePair = columns[1] + "-" + columns[2];
                edgesFromVertices.put(fromToNodePair, id);
                edges[id] = new Edge(x1, y1, x2, y2, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Determine if each edge is part of the shortest paths
        for (int i = 0; i < this.prev.length; i++) {
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
        f.setSize(640, 480);
        f.setTitle("Isochrone Map");

        Visualizer v = new Visualizer(vertices, edges);
        f.add(v);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    /**
     * Return the number of vertices.
     * @param filePathVertices file path for vertices.csv
     * @return the number of vertices
     */
    private int getV(String filePathVertices) throws IOException {
        return (int) Files.lines(Paths.get(filePathVertices)).count() - 1;
    }




    /**
     * Test client.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        String filePathVertices = args[0];
        String filePathEdges = args[1];
        int sourceVertexId = Integer.parseInt(args[2]);
        int thresholdDist = Integer.parseInt(args[3]);

        try {
            IsochroneMap isochroneMap = new IsochroneMap(filePathVertices,
                    filePathEdges, sourceVertexId, thresholdDist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
