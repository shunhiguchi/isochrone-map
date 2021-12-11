import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * The Edge class represents an edge in a directed digraph. In addition, it
 * contains methods and attributes for isochrone map visualization.
 */
public class Edge {

    /* Color of a line representing the edge. */
    private static final Color color = new Color(100, 149, 237);

    /*
     * Stroke of the line if it is part of the shortest paths for vertices
     * reachable within the threshold cost.
     */
    private static final BasicStroke strokeSolid = new BasicStroke();

    /*
     * Stroke of the line if it is not part of the shortest paths for vertices
     * reachable within the threshold cost.
     */
    private static final float[] dash = {2f, 0f, 2f};
    private static final BasicStroke strokeDashed = new BasicStroke(
            1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,
            1.0f, Edge.dash, 2f
    );

    /* id of the edge. */
    public final int id;

    /* cost of the edge. */
    public final int cost;

    /* Vertex at the start of the edge. */
    public Vertex v1;

    /* Vertex at the end of the edge. */
    public Vertex v2;

    /*
     * The edge is part of the shortest paths to vertices reachable within the
     * threshold cost.
     */
    public final boolean isShortestPath;

    /*
     * Constructor.
     */
    public Edge(int id, Vertex v1, Vertex v2, int cost) {
        this.id = id;
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
    }

    /*
     * Draw the edge. Different line strokes are used depending on if it is part
     * of the shortest paths to vertices reachable within the threshold cost.
     */
    public void draw(Graphics2D g2d) {
        Line2D.Double line = new Line2D.Double(this.v1.x, this.v1.y, this.v2.x, this.v2.y);
        if (this.isShortestPath) g2d.setStroke(Edge.strokeSolid);
        else g2d.setStroke(Edge.strokeDashed);
        g2d.setColor(Edge.color);
        g2d.draw(line);
    }
}
