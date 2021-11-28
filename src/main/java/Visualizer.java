import java.awt.BasicStroke;
import java.awt.Color;
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
        Ellipse2D.Double e = new Ellipse2D.Double(50, 75, 25, 25);
        g2d.setColor(new Color(100, 149, 237));
        g2d.fill(e);
    }

//    protected void drawEdge(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        Line2D l = new Line2D(100)
//    }

    public static void main(String[] args) {
        int w = 640;
        int h = 480;

        JFrame f = new JFrame();
        Visualizer v = new Visualizer(w, h);
        f.setSize(w, h);
        f.setTitle("Isochrone Map");
        f.add(v);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

class Vertex {
    private final double x;
    private final double y;
    private final boolean within;
    private final boolean source;

    private static final int R = 50;
    private static final Color color = new Color(100, 149, 237);

    public Vertex(int x, int y, int id, boolean within, boolean source) {
        this.x = x - (double) R / 2;
        this.y = y - (double) R / 2;
        this.id = id;
        this.within = within;
        this.source = source;
    }

    public void drawVertex(Graphics2D g2d) {
        Ellipse2D.Double e = new Ellipse2D.Double(this.x, this.y, R, R);
        g2d.setColor(color);
        g2d.fill(e);
    }
}

class Edge {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final boolean used;

    private static final Color color = new Color(100, 149, 237);

    private static final BasicStroke bsDefault = new BasicStroke();

    private float[] dash = {2f, 0f, 2f};
    private static final BasicStroke bsDashed = new BasicStroke(
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f
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