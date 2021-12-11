/**
 * The Vertex class represents a vertex in a directed digraph. In addition, it
 * contains methods and attributes for isochrone map visualization.
 */
public class Vertex {

    /* Radius of a circle representing the vertex. */
    private static final int R = 40;

    /* id of the vertex. */
    public final int id;

    /* x coordinate of the vertex. */
    public final int x;

    /* y coordinate of the vertex. */
    public final int y;

    /* the vertex is a source vertex. */
    public boolean isSource;

    /* The vertex is reachable within the threshold cost. */
    public boolean isReachable;

    /* x coordinate of the top left corner of the vertex bound shape. */
    private final float xtl;

    /* y coordinate of the top left corner of the vertex bound shape. */
    private final float ytl;

    /*
     * Constructor.
     */
    public Vertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xtl = x - (float) R / 2;
        this.ytl = y - (float) R / 2;
    }
}