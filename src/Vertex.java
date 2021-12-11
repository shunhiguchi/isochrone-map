import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.util.Comparator;

/**
 * The Vertex class represents a vertex in a directed digraph. In addition, it
 * contains methods and attributes for isochrone map visualization.
 */
public class Vertex implements Comparator<Vertex> {

    /* Radius of a circle representing the vertex. */
    private static final int R = 40;

    /* Color of the circle of the vertex is reachable within the threshold cost. */
    private static final Color colorDark = new Color(100, 149, 237);

    /* Color of the circle of the vertex is not reachable within the threshold cost. */
    private static final Color colorLight = Color.WHITE;

    /* Color of the circle of the vertex is a source vertex. */
    private static final Color colorSource = new Color(31, 102, 229);

    /* Stroke of the circle outline */
    private static final BasicStroke strokeDefault = new BasicStroke();

    /* id of the vertex. */
    public int id;

    /* x coordinate of the vertex. */
    public int x;

    /* y coordinate of the vertex. */
    public int y;

    /* Cumulative cost to the vertex from the source vertex. */
    public int cost;

    /* Previous vertex in the shortest path. */
    public Vertex prev;

    /* the vertex is a source vertex. */
    public boolean isSource;

    /* The vertex is reachable within the threshold cost. */
    public boolean isReachable;

    /* x coordinate of the top left corner of the vertex bound shape. */
    private float xtl;

    /* y coordinate of the top left corner of the vertex bound shape. */
    private float ytl;

    /*
     * Constructor.
     */
    public Vertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xtl = x - (float) R / 2;
        this.ytl = y - (float) R / 2;
        this.isSource = false;
        this.isReachable = false;
        this.cost = Integer.MAX_VALUE;
    }

    public Vertex() {}

    /*
     * Draw the vertex. Different colours are used depending on if the vertex is
     * a source vertex and if the vertex is reachable within the threshold cost.
     */
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double e = new Ellipse2D.Double(this.xtl, this.ytl, Vertex.R, Vertex.R);

        /* Draw the circle fill. */
        if (this.isSource) g2d.setColor(Vertex.colorSource);
        else if (this.isReachable) g2d.setColor(Vertex.colorDark);
        else g2d.setColor(Vertex.colorLight);
        g2d.fill(e);

        /* Draw the circle outline if necessary. */
        if (!this.isReachable) {
            g2d.setColor(Vertex.colorDark);
            g2d.setStroke(Vertex.strokeDefault);
            g2d.draw(e);
        }

        /* Add the vertex id in the centre of the circle. */
        if (this.isReachable) g2d.setColor(Vertex.colorLight);
        else g2d.setColor(Vertex.colorDark);

        Font f = g2d.getFont();
        FontMetrics fm = g2d.getFontMetrics(f);
        String idString = Integer.toString(this.id);
        Rectangle2D rect = fm.getStringBounds(idString, g2d);

        int textHeight = (int)(rect.getHeight());
        int textWidth  = (int)(rect.getWidth());
        float xid = this.x - (float) textWidth / 2;
        float yid = this.y - (float) textHeight / 2 + fm.getAscent();

        g2d.drawString(idString, xid, yid);
    }

    /*
     * Compare two vertices based on their cumulative costs.
     */
    @Override
    public int compare(Vertex v1, Vertex v2) {
        return Integer.compare(v1.cost, v2.cost);
    }
}