
package control;

import graph.EdgeData;
import graph.Graph;
import graph.VerticeData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.io.Serializable;
import java.util.ArrayList;
import view.FrameCanvas;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class GraphCanvas implements Serializable
{
    private String name;
    private Color verticeColor = Color.white;
    
    VerticeItem activeVertice;
    VerticeItem secondActiveVertice;
    EdgeItem activeEdge;

    private final Float WIDTH = 10f;
    private final Float HEIGHT = 10f;
    private static int colorIndex = 0;
    
    public class VerticeItem implements Serializable
    {

        String name;
        float x, y;
        Ellipse2D.Float vertice;

        public VerticeItem(String name, float x, float y)
        {
            this.name = name;
            this.x = x;
            this.y = y;
            this.vertice = new Ellipse2D.Float();
            //this.reset();
        }

        private void reset(FrameCanvas view)
        {
            Float offsetX = view.getOffsetX();
            Float offsetY = view.getOffsetY();
            Float fator = view.getFator();
            vertice.x = ((x - WIDTH / 2f) + offsetX) * fator;
            vertice.y = ((y - HEIGHT / 2f) + offsetY) * fator;
            vertice.width = WIDTH * fator;
            vertice.height = HEIGHT * fator;
        }
    }

    public class EdgeItem implements Serializable
    {

        String name;
        String cost;
        String nameIn, nameOut;
        double x1, y1, x2, y2;
        QuadCurve2D.Double edge;
        Line2D.Double aArrow;
        Line2D.Double bArrow;
        Ellipse2D.Double auxDest;

        public EdgeItem(String name, String cost, String nameIn, String nameOut, double x1, double y1, double x2, double y2)
        {
            this.name = name;
            this.edge = new QuadCurve2D.Double();
            this.aArrow = new Line2D.Double();
            this.bArrow = new Line2D.Double();
            this.auxDest = new Ellipse2D.Double();
            this.cost = cost;
            this.nameIn = nameIn;
            this.nameOut = nameOut;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            //this.reset();
        }

        private void reset(FrameCanvas view)
        {
            Float offsetX = view.getOffsetX();
            Float offsetY = view.getOffsetY();
            Float fator = view.getFator();
            double x0 = (x1 + x2) / 2;
            double y0 = (y1 + y2) / 2;
            double vx = x2 - x0;
            double vy = y2 - y0;
            double fx = vy + x0;
            double fy = -vx + y0;
            edge.x1 = (x1 + offsetX) * fator;
            edge.y1 = (y1 + offsetY) * fator;
            edge.ctrlx = (fx + offsetX) * fator;
            edge.ctrly = (fy + offsetY) * fator;
            edge.x2 = (x2 + offsetX) * fator;
            edge.y2 = (y2 + offsetY) * fator;

            // Setinha
            auxDest.x = edge.getX2() - WIDTH * fator / 2f;
            auxDest.y = edge.getY2() - HEIGHT * fator / 2f;
            auxDest.width = WIDTH * fator;
            auxDest.height = HEIGHT * fator;
            Point2D inters = getIntersectionPoint(edge, auxDest);
            float arrowBaseX = (3f / 2f) * (float) inters.getX() - (1f / 2f) * (float) auxDest.getCenterX();
            float arrowBaseY = (3f / 2f) * (float) inters.getY() - (1f / 2f) * (float) auxDest.getCenterY();

            aArrow.x1 = (float) inters.getX();
            aArrow.y1 = (float) inters.getY();
            aArrow.x2 = (arrowBaseY - (float) inters.getY()) + arrowBaseX;
            aArrow.y2 = -(arrowBaseX - (float) inters.getX()) + arrowBaseY;

            bArrow.x1 = (float) inters.getX();
            bArrow.y1 = (float) inters.getY();
            bArrow.x2 = -(arrowBaseY - (float) inters.getY()) + arrowBaseX;
            bArrow.y2 = (arrowBaseX - (float) inters.getX()) + arrowBaseY;
        }
    }
    ArrayList<VerticeItem> verticeList = new ArrayList<VerticeItem>();
    ArrayList<EdgeItem> edgeList = new ArrayList<EdgeItem>();

    public GraphCanvas(String nome)
    {
        super();

        //this.view = view;
        name = nome;
        Graph g = MainWindowControl.getUniqueInstance().getGraphModel().getGraph(nome);

        for (Object o : g.getVerticesData())
        {
            if (o instanceof VerticeData)
            {
                VerticeData vd = (VerticeData) o;

                //sempre deve chegar ate aqui
                addVertice(vd.getName(), (float) vd.getX(), (float) vd.getY());
            }
        }

        for (Object o : g.getEdgesData())
        {
            if (o instanceof EdgeData)
            {
                EdgeData vd = (EdgeData) o;

                float x0, y0;
                float xf, yf;
                String nameIn, nameOut;

                try
                {
                    Object inputObj = g.getInputVertice(vd);
                    Object outputObj = g.getOutputVertice(vd);

                    if (inputObj instanceof VerticeData)
                    {
                        VerticeData inputVertice = (VerticeData) inputObj;

                        x0 = inputVertice.getX();
                        y0 = inputVertice.getY();
                        nameIn = inputVertice.getName();

                        if (outputObj instanceof VerticeData)
                        {
                            VerticeData outputVertice = (VerticeData) outputObj;

                            xf = outputVertice.getX();
                            yf = outputVertice.getY();
                            nameOut = outputVertice.getName();

                            //sempre deve chegar ate aqui
                            addEdge(vd.getName(), String.valueOf(vd.getCost()), nameIn, nameOut, x0, y0, xf, yf);
                        }
                    }
                } catch (Exception ex)
                {
                }

            }
        }
        
        setNewColor();

    }

    public void addVertice(String name, Float x, Float y)
    {
        VerticeItem v = new VerticeItem(name, x, y);
        verticeList.add(v);
    }

    public void addEdge(String name, String cost, String nameIn, String nameOut, Float x1, Float y1, Float x2, Float y2)
    {
        EdgeItem e = new EdgeItem(name, cost, nameIn, nameOut, x1, y1, x2, y2);
        edgeList.add(e);
    }

    public void paint(Graphics2D ga, FrameCanvas view)
    {
        ga.setPaint(Color.red);
        for (EdgeItem edge : edgeList)
        {
            edge.reset(view);
            ga.draw(edge.edge);
            ga.draw(edge.aArrow);
            ga.draw(edge.bArrow);
            ga.drawString(edge.cost, (float)edge.edge.ctrlx, (float)edge.edge.ctrly);
        }

        for (VerticeItem vertice : verticeList)
        {
            vertice.reset(view);
            ga.setPaint(Color.BLACK);
            ga.draw(vertice.vertice);
            ga.setPaint( verticeColor );
            ga.fill(vertice.vertice);
            ga.setPaint(Color.BLACK);
            ga.drawString(vertice.name, (float)vertice.vertice.getCenterX(), (float)vertice.vertice.getCenterY());
        }

    }

    private Point2D.Double getIntersectionPoint(Shape s1, Shape s2)
    {
        Point2D.Double closest = new Point2D.Double();
        double min = Double.MAX_VALUE;
        // O flatness determina a precisão do procedimento
        double flatness = 0.01;
        PathIterator pit = s1.getPathIterator(null, flatness);
        double[] coords = new double[6];
        while (!pit.isDone())
        {
            pit.currentSegment(coords);
            Point2D.Double p = getClosestPoint(s2, coords[0], coords[1]);
            double distance = p.distance(coords[0], coords[1]);
            if (distance < min)
            {
                min = distance;
                closest.setLocation(coords[0], coords[1]);
            }
            pit.next();
        }
        return closest;
    }

    private Point2D.Double getClosestPoint(Shape s, double x, double y)
    {
        Point2D.Double closest = new Point2D.Double();
        double min = Double.MAX_VALUE;
        double flatness = 0.01;
        PathIterator pit = s.getPathIterator(null, flatness);
        double[] coords = new double[6];
        while (!pit.isDone())
        {
            pit.currentSegment(coords);
            double distance = Point2D.distance(x, y, coords[0], coords[1]);
            if (distance < min)
            {
                min = distance;
                closest.setLocation(coords[0], coords[1]);
            }
            pit.next();
        }
        return closest;
    }
    
    public String getName()
    {
        return name;
    }
    
    public final void setNewColor()
    {
        int iColor = colorIndex%5;
        Color color = Color.white;
        switch ( iColor )
        {
            case 0:
                color = Color.green;
                break;
            case 1:
                color = Color.cyan;
                break;
            case 2:
                color = Color.red;
                break;
            case 3:
                color = Color.blue;
                break;
            case 4:
                color = Color.yellow;
                break;
            default:
                color = Color.white;
                break;
        }
        verticeColor = color;
        colorIndex++;
    }
    
    public boolean ActivateVertice( float x, float y )
    {
        activeVertice = null;
        boolean collide = false;
        for ( VerticeItem vertice: verticeList )
        {
            if (vertice.vertice.contains(x, y))
            {
                activeVertice = vertice;
                collide = true;
                break;
            }
        }
        return collide;
    }
    
    public boolean ActivateSecondVertice( float x, float y )
    {
        secondActiveVertice = null;
        boolean collide = false;
        for ( VerticeItem vertice: verticeList )
        {
            if (vertice.vertice.contains(x, y))
            {
                secondActiveVertice = vertice;
                collide = true;
                break;
            }
        }
        
        return collide;
    }
    
    public boolean activateEdge( float x, float y )
    {
        activeEdge = null;
        boolean collide = false;
        for ( EdgeItem edge: edgeList )
        {
            if (edge.edge.contains(x, y))
            {
                activeEdge = edge;
                collide = true;
                break;
            }
        }
        return collide;
    }
    
    public void ConnectWithEdge(String name)
    {
        String n1 = activeVertice.name;
        Float x1 = activeVertice.x;
        Float y1 = activeVertice.y;
        String n2 = secondActiveVertice.name;
        Float x2 = secondActiveVertice.x;
        Float y2 = secondActiveVertice.y;
        addEdge(name, "0", n1, n2, x1, y1, x2, y2);
    }
    
    public void moveActiveVertice( float x, float y )
    {
        activeVertice.x += x;
        activeVertice.y += y;
        for ( EdgeItem edge: edgeList)
        {
            if ( edge.nameIn.compareTo(activeVertice.name) == 0 )
            {
                edge.x1 = activeVertice.x;
                edge.y1 = activeVertice.y;
            }
            if ( edge.nameOut.compareTo(activeVertice.name) == 0 )
            {
                edge.x2 = activeVertice.x;
                edge.y2 = activeVertice.y;
            }
        }
    }
    
    public void removeActiveVertice()
    {
        String verticeName = activeVertice.name;
        ArrayList<EdgeItem> removableEdges = new ArrayList<EdgeItem>();
        for ( EdgeItem edge: edgeList )
        {
            if ( edge.nameIn.compareTo(verticeName) == 0  ||
                 edge.nameOut.compareTo(verticeName) == 0  )
            {
                removableEdges.add(edge);
            }
        }
        for ( EdgeItem edge: removableEdges )
        {
            edgeList.remove(edge);
        }
        verticeList.remove(activeVertice);
        activeVertice = null;
    }
    
    public void removeActiveEdge()
    {
        edgeList.remove(activeEdge);
        activeEdge = null;
    }
    
    public String getActiveVerticeName()
    {
        return activeVertice.name;
    }

    public String getActiveEdgeName()
    {
        return activeEdge.name;
    }
    
    public String getSecondActiveVerticeName()
    {
        return secondActiveVertice.name;
    }
    
    public String getActiveEdgeOrigin()
    {
        return activeEdge.nameIn;
    }
    
    public String getActiveEdgeDestination()
    {
        return activeEdge.nameOut;
    }

    public void setActiveEdgeCost(double cost)
    {
        activeEdge.cost = String.valueOf(cost);
    }
}
