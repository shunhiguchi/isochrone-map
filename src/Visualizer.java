import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

public class Visualizer extends JComponent {

    private final Vertex[] vertices;
    private final Edge[] edges;

    public Visualizer (Vertex[] vertices, Edge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

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
        for (Vertex v : this.vertices) v.drawEdge(g2d);

    }
}

class Vertex {
    final int x; // x coordinate of a centre of a circle
    final int y; // y coordinate of a centre of a circle
    private final float xtl; // x coordinate of a top left bound of a circle
    private final float ytl; // y coordinate of a top left bound of a circle
    private final String id; // id of a vertex
    private final boolean reachable; // vertex reachable within a specified cost
    private final boolean source; // vertex is a source vertex

    // Radius of a circle
    private static final int R = 50;

    // Color of a circle if reachable is true
    private static final Color colorReachable = new Color(100, 149, 237);

    // Color of a circle if reachable is false
    private static final Color colorNotReachable = Color.WHITE;

    // Color of a circle for a source vertex
    private static final Color colorSource = new Color(31,102,229);

    // Stroke of a circle outline
    private static final BasicStroke bsOutline = new BasicStroke();

    public Vertex(int x, int y, int id, boolean reachable, boolean source) {
        this.x = x;
        this.y = y;
        this.xtl = x - (float) R / 2;
        this.ytl = y - (float) R / 2;
        this.id = Integer.toString(id);
        this.reachable = reachable;
        this.source = source;
    }

    public void drawVertex(Graphics2D g2d) {

        // Draw a circle
        Ellipse2D.Double e = new Ellipse2D.Double(this.xtl, this.ytl, Vertex.R, Vertex.R);
        if (reachable) {
            if (source) g2d.setColor(Vertex.colorSource);
            else g2d.setColor(Vertex.colorReachable);
            g2d.fill(e);
        }
        else {
            g2d.setColor(Vertex.colorNotReachable);
            g2d.fill(e);
            g2d.setColor(Vertex.colorReachable);
            g2d.setStroke(bsOutline);
            g2d.draw(e);
        }

        // Draw a vertex ID next to a circle
        g2d.getFont().getSize2D();
        g2d.drawString(this.id, this.x, this.y);
    }
}

class Edge {
    private final int x1; // x coordinate of one end of a line
    private final int y1; // y coordinate of one end of a line
    private final int x2; // x coordinate of the other end of a line
    private final int y2; // y coordinate of the other end of a line
    private final boolean used; // edge is used for shortest paths

    // Set a color of a line
    private static final Color color = new Color(100, 149, 237);

    // Set a stroke of a line if it is used for shortest paths
    private static final BasicStroke bsDefault = new BasicStroke();

    // Set a stroke of a line if it is not used for shortest paths
    private static final float[] dash = {2f, 0f, 2f};
    private static final BasicStroke bsDashed = new BasicStroke(
            1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, Edge.dash, 2f
    );

    public Edge(int x1, int y1, int x2, int y2, boolean used) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.used = used;
    }

    public void drawEdge(Graphics2D g2d) {
        // Draw a line
        Line2D.Double line = new Line2D.Double(this.x1, this.y1, this.x2, this.y2);
        if (used) g2d.setStroke(bsDefault); else g2d.setStroke(bsDashed);
        g2d.setColor(color);
        g2d.draw(line);
    }
}