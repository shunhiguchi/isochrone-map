import java.awt.Color;
import java.awt.Graphics;

import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Visualizer extends JComponent {
    private int width;
    private int height;
    public Visualizer (int width, int height) {
        this.width = width;
        this.height = height;
    }
    protected void drawEdge(Graphics g) {
        
    }
    public static void main(String[] args) {
        int w = 640;
        int h = 480;

        JFrame f = new JFrame();
        f.setSize(w, h);
        f.setTitle("Isochrone Map");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
