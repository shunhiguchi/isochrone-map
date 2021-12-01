/**
 * ShortestPath.java
 *
 * Computes the shortest paths (single source) for a weighted directed graph.
 */

import java.util.*;

/**
 * The ShortestPath class provides a method for computing the single-source
 * shortest paths for a weighted directed graph. The Dijkstra's shortest path
 * algorithm is implemented with priority queues.
 */
public class ShortestPath {

    private final int dist[]; // Distances from the source vertex
    private final Set<Integer> prev; // Previously visited vertices
    private PriorityQueue<Node> pq; // Priority queue to store vertices

    private int V;
    List<List<Node>> adj;

    public ShortestPath(int V) {
        this.V = V;
        dist = new int[V];
        prev = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
    }

    /**
     * Dijkstra's shortest path algorithm.
     * @param adj adjacency-lists digraph representation
     * @param src source vertex ID
     * @return an integer array of distance from a source vertex to vertex i
     */
    public int[] dijkstra(List<List<Node>> adj, int src)     {
        this.adj = adj;

        // Initialize distances to each node to a maximum integer value
        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add a source vertex and set the distance to it as 0
        pq.add(new Node(src, 0));
        dist[src] = 0;

        // Iterate until all vertices are visited
        while (prev.size() != V) {
            // Terminate if the priority queue is empty
            if (pq.isEmpty()) break;

            /* Remove the minimum distance vertex from the priority queue and
             * add it to the list of visited vertices. Process neighbouring
             * vertices.
             */
            int u = pq.remove().node;

            if (prev.contains(u)) continue;

            prev.add(u);
            processNeighbours(u);
        }

        return dist;
    }

    /**
     * Process neighbouring nodes.
     * @param u a node of interest.
     */
    private void processNeighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            /* If the neighbouring node hasn't been previously processed,
             * compute the new distance and update the shortest distance to the
             * node if applicable.
             */
            if (!prev.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;

                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }
}

/**
 *  Node in the graph.
 */
class Node implements Comparator<Node> {

    public int node;
    public int cost;

    public Node() {}
    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    /**
     * Compare two nodes based on their costs.
     * @param n1 one of the two nodes
     * @param n2 the other node
     * @return -1, 0, or 1 representing comparison
     */
    @Override
    public int compare(Node n1, Node n2)     {
        if (n1.cost < n2.cost) return -1;
        else if (n1.cost > n2.cost) return 1;
        else return 0;
    }
}
