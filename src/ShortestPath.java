import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * The ShortestPath class provides a method for computing the single-source
 * shortest paths for a weighted directed graph. The Dijkstra's shortest path
 * algorithm is implemented with priority queues.
 */
public class ShortestPath {

    /* Map of vertices. */
    public final HashMap<Integer, Vertex> vertices;

    /* Map of edges. */
    public final HashMap<Integer, Edge> edges;

    /* Map of vertex adjacency list. */
    private final HashMap<Integer, HashMap<Integer, Edge>> adj;

    /* Previously visited vertices. */
    private final Set<Integer> visited;

    /* Priority queue to store vertices. */
    private final PriorityQueue<Vertex> pq;

    public ShortestPath(HashMap<Integer, Vertex> vertices, HashMap<Integer, Edge> edges,
                        HashMap<Integer, HashMap<Integer, Edge>> adj) {
        this.vertices = vertices;
        this.edges = edges;
        this.adj = adj;
        this.visited = new HashSet<>();
        this.pq = new PriorityQueue<>(vertices.size(), new Vertex());
    }

    /*
     * Dijkstra's shortest path algorithm.
     */
    public void dijkstra(Vertex sourceVertex)     {
        /*
         * Initialize cost to the source vertex to 0. Cost to other vertices
         * should already be initialized to infinity or Integer.MAX_VALUE.
         */
        this.vertices.get(sourceVertex.id).cost = 0;
        this.pq.add(this.vertices.get(sourceVertex.id));

        /* Iterate until all vertices have been visited. */
        while (this.visited.size() != this.vertices.size()) {
            if (this.pq.isEmpty()) break;

            /*
             * Remove the minimum distance vertex from the priority queue and
             * add it to the list of visited vertices. Process neighbouring
             * vertices.
             */
            Vertex u = this.pq.remove();
            if (this.visited.contains(u.id)) continue;
            this.visited.add(u.id);
            processNeighbours(u);
        }
    }

    /*
     * Process neighbouring nodes.
     */
    private void processNeighbours(Vertex u) {
        int newCost;
        for (Map.Entry<Integer, Edge> entry: this.adj.get(u.id).entrySet()) {

            /*
             * If the neighbouring node hasn't been previously processed,
             * compute the new distance and update the shortest distance to the
             * node if applicable.
             */
            if (!this.visited.contains(entry.getKey())) {
                newCost = u.cost + entry.getValue().cost;
                if (newCost < this.vertices.get(entry.getKey()).cost) {
                    this.vertices.get(entry.getKey()).cost = newCost;
                    this.vertices.get(entry.getKey()).prev = u;
                }
                pq.add(this.vertices.get(entry.getKey()));
            }
        }
    }
}
