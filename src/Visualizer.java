import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Visualizer extends JComponent {

    private int width;
    private int height;

    public Visualizer (int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        Edge e1 = new Edge(75, 75, 250, 75, true);
        Edge e2 = new Edge(75, 75, 75, 150, false);
        Vertex v1 = new Vertex(75, 75, 99, true, false);
        Vertex v2 = new Vertex(250, 75, 99, true, false);
        Vertex v3 = new Vertex(75, 150, 99, false, false);
        e1.drawEdge(g2d);
        e2.drawEdge(g2d);
        v1.drawVertex(g2d);
        v2.drawVertex(g2d);
        v3.drawVertex(g2d);
    }

    public static void main(String[] args) {
        int w = 640;
        int h = 480;

        JFrame f = new JFrame();
        f.setSize(w, h);
        f.setTitle("Isochrone Map");

        Visualizer v = new Visualizer(w, h);
        f.add(v);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

class Vertex {
    private final float x; // x coordinate of a vertex
    private final float y; // y coordinate of a vertex
    private final String id; // id of a vertex
    private final boolean reachable; // vertex reachable within a specified cost
    private final boolean source; // vertex is a source vertex

    // Radius of a circle
    private static final int R = 50;

    // Color of a vertex if reachable is true
    private static final Color colorReachable = new Color(100, 149, 237);

    // Color of a vertex if reachable is false
    private static final Color colorNotReachable = Color.WHITE;

    // Stroke of a vertex outline
    private static final BasicStroke bsOutline = new BasicStroke();

    public Vertex(int x, int y, int id, boolean reachable, boolean source) {
        this.x = x - (float) R / 2;
        this.y = y - (float) R / 2;
        this.id = Integer.toString(id);
        this.reachable = reachable;
        this.source = source;
    }

    public void drawVertex(Graphics2D g2d) {

        // Draw a circle
        Ellipse2D.Double e = new Ellipse2D.Double(this.x, this.y, Vertex.R, Vertex.R);
        if (reachable) {
            g2d.setColor(Vertex.colorReachable);
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

    // Set a color of an edge
    private static final Color color = new Color(100, 149, 237);

    // Set a stroke of an edge if it is used for shortest paths
    private static final BasicStroke bsDefault = new BasicStroke();

    // Set a stroke of an edge if it is not used for shortest paths
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
        Line2D.Double line = new Line2D.Double(this.x1, this.y1, this.x2, this.y2);
        if (used) g2d.setStroke(bsDefault); else g2d.setStroke(bsDashed);
        g2d.setColor(color);
        g2d.draw(line);
    }
}