/**
 * ShortestPath.java
 *
 * Computes the shortest paths (single source) for a weighted directed graph.
 */

package shunhiguchi;

import java.util.*;

/**
 * The ShortestPath class provides a method for computing the single-source
 * shortest paths for a weighted directed graph.
 */
public class ShortestPath {
    /*
     * Dijkstra's shortest path algorithm implemented with priority queues.
     */

    private int dist[];
    private Set<Integer> prev;
    private PriorityQueue<Node> pq;

    private int V;
    List<List<Node>> adj;

    public ShortestPath(int V) {
        fthis.V = V;
        dist = new int[V];
        prev = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
    }

    /**
     * Dijkstra's shortest path algorithm.
     * @param adj
     * @param src
     */
    public void dijkstra(List<List<Node>> adj, int src)     {
        this.adj = adj;

        /* Initialize distances to each node to a maximum integer value. */
        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        /* Add a source node and set the distance to it as 0. */
        pq.add(new Node(src, 0));
        dist[src] = 0;

        while (prev.size() != V) {
            /* Terminate if the priority queue is empty. */
            if (pq.isEmpty()) return;

            /* Remove the minimum distance node from the priority queue and
             * add it to the list of previous nodes. Process neighbouring nodes.
             */
            int u = pq.remove().node;

            if (prev.contains(u)) continue;

            prev.add(u);
            processNeighbours(u);
        }
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

    public static void main(String arg[]) {
        int V = 5;
        int source = 0;

        // Adjacency list representation of the
        // connected edges by declaring List class object
        // Declaring object of type List<Node>
        List<List<Node> > adj
                = new ArrayList<List<Node> >();

        // Initialize list for every node
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        // Inputs for the GFG(dpq) graph
        adj.get(0).add(new Node(1, 9));
        adj.get(0).add(new Node(2, 6));
        adj.get(0).add(new Node(3, 5));
        adj.get(0).add(new Node(4, 3));

        adj.get(2).add(new Node(1, 2));
        adj.get(2).add(new Node(3, 4));

        // Calculating the single source shortest path
        GFG dpq = new GFG(V);
        dpq.dijkstra(adj, source);

        // Printing the shortest path to all the nodes
        // from the source node
        System.out.println("The shorted path from node :");

        for (int i = 0; i < dpq.dist.length; i++)
            System.out.println(source + " to " + i + " is "
                    + dpq.dist[i]);
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
    @Override public int compare(Node n1, Node n2)     {
        if (n1.cost < n2.cost) return -1;
        else if (n1.cost > n2.cost) return 1;
        else return 0;
    }
}
