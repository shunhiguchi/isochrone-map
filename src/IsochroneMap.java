import javax.swing.JComponent;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

/**
 * Identify nodes accessible within a threshold distance.
 * Usage: IsochroneMap vertices.csv edges.csv 0 10
 * @param args
 */
public class IsochroneMap {

    private int V;
    private int[] dist;
    private int[] prev;
    private int sourceVertexId;
    private int thresholdDist;

    public IsochroneMap(String filePathVertices, String filePathEdges, int sourceVertexId, int thresholdDist) throws IOException {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Compute the shortest paths
        ShortestPath sp = new ShortestPath(V);
        List<int[]> temp = sp.dijkstra(adj, this.sourceVertexId);
        this.dist = temp.get(0);
        this.prev = temp.get(1);


        /**
         * Mark nodes with their shortest path distances within the threshold
         * distance. Mark edges used as part of the shortest paths.
         */

        /**
         * Export the nodes and edges.
         */

        /**
         * Visualize the isochrone map.
         */
        System.out.println("dist\tprev");
        for (int i = 0; i < dist.length; i++)
            System.out.println(dist[i] + "\t" + prev[i]);
    }

    /**
     * Return the number of vertices.
     * @param filePathVertices file path for vertices.csv
     * @return the number of vertices
     */
    private int getV(String filePathVertices) throws IOException {
        return (int) Files.lines(Paths.get(filePathVertices)).count() - 1;
    }

//    void visualizeIsochroneMap() {
//        JFrame f = new JFrame();
//        f.setSize(w, h);
//        f.setTitle("Isochrone Map");
//
//        f.add(new Visualizer());
//
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);
//    }

    /**
     * Test client.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        String filePathVertices = args[0];
        String filePathEdges = args[1];
        int startVertexId = Integer.parseInt(args[2]);
        int thresholdDist = Integer.parseInt(args[3]);

        try {
            IsochroneMap isochroneMap = new IsochroneMap(filePathVertices,
                    filePathEdges, startVertexId, thresholdDist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
