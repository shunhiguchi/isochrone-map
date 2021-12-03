import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * The Visualizer class provides a means to visualize an isochrone map
 * represented by a digraph. It distinguishes if vertices are reachable within
 * a threshold cost, and if edges are used as part of the shortest paths to the
 * reachable vertices.
 */
public class Visualizer extends JComponent {

    private final Vertex[] vertices;
    private final Edge[] edges;

    /**
     * Instantiate this class with an array of vertices and edges.
     * @param vertices an array of Vertex objects
     * @param edges an array of Edge objects
     */
    public Visualizer (Vertex[] vertices, Edge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    /**
     * Draw vertices and edges. Implicitly called.
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        // Draw edges
        for (Edge e : this.edges) e.drawEdge(g2d);

        // Draw vertices
        for (Vertex v : this.vertices) v.drawVertex(g2d);

    }
}

/**
 * The Vertex class provides utilities to visualize vertices.
 */
class Vertex {
    public final String id; // id of a vertex
    public final int x; // x coordinate of a centre of a circle
    public final int y; // y coordinate of a centre of a circle
    public boolean reachable; // vertex reachable within a specified cost
    public final boolean source; // vertex is a source vertex

    private final float xtl; // x coordinate of a top left bound of a circle
    private final float ytl; // y coordinate of a top left bound of a circle

    // Radius of a circle
    private static final int R = 40;

    // Color of a circle if reachable is true
    private static final Color colorDark = new Color(100, 149, 237);

    // Color of a circle if reachable is false
    private static final Color colorLight = Color.WHITE;

    // Color of a circle for a source vertex
    private static final Color colorSource = new Color(31,102,229);

    // Stroke of a circle outline
    private static final BasicStroke bsDefault = new BasicStroke();

    /**
     * Instantiate this class with information related to a vertex.
     * @param id id of a vertex
     * @param x x coordinate of a vertex
     * @param y y coordinate of a vertex
     * @param reachable vertex is reachable within a threshold cost
     * @param source vertex is a source vertex
     */
    public Vertex(int id, int x, int y, boolean reachable, boolean source) {
        this.id = Integer.toString(id);
        this.x = x;
        this.y = y;
        this.xtl = x - (float) R / 2;
        this.ytl = y - (float) R / 2;
        this.reachable = reachable;
        this.source = source;
    }

    /**
     * Draw a vertex. Different colors are used depending on if a vertex is a
     * source vertex, reachable, or not reachable.
     * @param g2d Graphics2D
     */
    public void drawVertex(Graphics2D g2d) {

        // Draw a circle
        Ellipse2D.Double e = new Ellipse2D.Double(this.xtl, this.ytl, Vertex.R, Vertex.R);
        if (this.source) g2d.setColor(Vertex.colorSource);
        else if (this.reachable) g2d.setColor(Vertex.colorDark);
        else g2d.setColor(Vertex.colorLight);
        g2d.fill(e);

        // Add an outline to the circle if light fill color is used
        if (!this.reachable) {
            g2d.setColor(Vertex.colorDark);
            g2d.setStroke(Vertex.bsDefault);
            g2d.draw(e);
        }

        // Add a vertex ID in the centre of the circle
        if (this.reachable) g2d.setColor(Vertex.colorLight);
        else g2d.setColor(Vertex.colorDark);

        Font f = g2d.getFont();
        FontMetrics fm = g2d.getFontMetrics(f);
        Rectangle2D rect = fm.getStringBounds(this.id, g2d);
        int textHeight = (int)(rect.getHeight());
        int textWidth  = (int)(rect.getWidth());
        float xid = this.x - (float) textWidth / 2;
        float yid = this.y - (float) textHeight / 2 + fm.getAscent();

        g2d.drawString(this.id, xid, yid);
    }
}

/**
 * The Edge class provides utilities to visualize edges.
 */
class Edge {
    public final String id; // id of a line
    public final int x1; // x coordinate of one end of a line
    public final int y1; // y coordinate of one end of a line
    public final int x2; // x coordinate of the other end of a line
    public final int y2; // y coordinate of the other end of a line
    public boolean used; // edge is used for shortest paths

    // Set a color of a line
    private static final Color color = new Color(100, 149, 237);

    // Set a stroke of a line if it is used for shortest paths
    private static final BasicStroke bsDefault = new BasicStroke();

    // Set a stroke of a line if it is not used for shortest paths
    private static final float[] dash = {2f, 0f, 2f};
    private static final BasicStroke bsDashed = new BasicStroke(
            1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, Edge.dash, 2f
    );

    /**
     * Instantiate this class with information related to an edge.
     * @param id if of an edge
     * @param x1 x coordinate of one end of a line
     * @param y1 y coordinate of one end of a line
     * @param x2 x coordinate of the other end of a line
     * @param y2 y coordinate of the other end of a line
     * @param used edge is used for shortest paths
     */
    public Edge(int id, int x1, int y1, int x2, int y2, boolean used) {
        this.id = Integer.toString(id);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.used = used;
    }

    /**
     * Draw an edge. Different strokes are used depending on if the edge is used
     * as part of the shortest paths for vertices reachable within a threshold
     * cost.
     * @param g2d Graphics2D
     */
    public void drawEdge(Graphics2D g2d) {
        // Draw a line
        Line2D.Double line = new Line2D.Double(this.x1, this.y1, this.x2, this.y2);
        if (this.used) g2d.setStroke(Edge.bsDefault); else g2d.setStroke(Edge.bsDashed);
        g2d.setColor(Edge.color);
        g2d.draw(line);
    }
}