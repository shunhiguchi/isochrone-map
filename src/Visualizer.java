import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import javax.swing.JComponent;

/**
 * The Visualizer class provides a means to visualize an isochrone map
 * represented by a digraph.
 */
public class Visualizer extends JComponent {

    /* Map of vertices. */
    private final HashMap<Integer, Vertex> vertices;

    /* Map of edges. */
    private final HashMap<Integer, Edge> edges;

    /*
     * Constructor.
     */
    public Visualizer (HashMap<Integer, Vertex> vertices, HashMap<Integer, Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    /*
     * Draw vertices and edges. Implicitly called.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        for (Edge e : this.edges.values()) e.draw(g2d);
        for (Vertex v : this.vertices.values()) v.draw(g2d);
    }
}
