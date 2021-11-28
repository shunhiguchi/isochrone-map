import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Visualizer extends JComponent {

    private int width;
    private int height;

    public Visualizer (int width, int height) {
        this.width = width;
        this.height = height;
    }

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
    private double x;
    private double y;
    private boolean within;
    private boolean source;

    private static final int R = 50;
    private static final Color color = new Color(100, 149, 237);

    public Vertex(int x, int y, boolean within, boolean source) {
        this.x = x - (double) R / 2;
        this.y = y - (double) R / 2;
        this.within = within;
        this.source = source;
    }

    public void drawVertex(Graphics2D g2d) {
        Ellipse2D.Double e = new Ellipse2D.Double(this.x, this.y, R, R);
        g2d.setColor(color);
        g2d.fill(e);
    }
}