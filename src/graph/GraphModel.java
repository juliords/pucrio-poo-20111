
package graph;

import graph.exception.AlreadyHasEdge;
import graph.exception.MissingEdge;
import graph.exception.MissingVertice;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class GraphModel
{

    public final static String SUFFIX = ".grf";
    private HashMap<String, Graph> graphList = new HashMap<String, Graph>();
    private String lastName = null;
    private ArrayList<ActionListener> observerList = new ArrayList<ActionListener>();
    private ArrayList<ActionListener> checklistObservers = new ArrayList<ActionListener>();

    public void load(String path, String name) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        name = name.split(SUFFIX)[0];

        if (this.graphList.get(name) != null)
        {
            System.out.println("Arquivo ja carregado...");
            return;
        }

        Graph graph = Graph.load(path);

        this.graphList.put(name, graph);
        this.lastName = name;

        //notifico as checklists para informar que um novo grafo foi adicionado
        for (ActionListener el : checklistObservers)
        {
            el.actionPerformed(null);
        }
    }

    public void addChecklistListener(ActionListener al)
    {
        this.checklistObservers.add(al);
    }

    public void addListener(ActionListener al)
    {
        this.observerList.add(al);
    }

    public void notifyListeners()
    {
        for (ActionListener el : observerList)
        {
            el.actionPerformed(null);
        }
    }

    public Graph getGraph(String pathGraph)
    {
        if(this.graphList.get(pathGraph) != null)
        {
            return this.graphList.get(pathGraph).clone();
        }
        return null;
    }

    public boolean addGraph(Graph graph, String name)
    {

        if (this.graphList.get(name) != null)
        {
            System.out.println("Arquivo ja carregado...");
            return false;
        }

        this.graphList.put(name, graph);
        
        this.lastName = name;

        //notifico as checklists para informar que um novo grafo foi adicionado
        for (ActionListener el : checklistObservers)
        {
            el.actionPerformed(null);
        }

        return true;
    }

    public String getLastIndex()
    {
        return this.lastName;
    }

    public Set<String> getGraphNames()
    {
        return this.graphList.keySet();
    }
    
    public void addVerticeTo( String graphName, VerticeData vertice )
            throws graph.exception.AlreadyHasVertice, graph.exception.NotUniqueName
    {
        Graph graph = graphList.get(graphName);
        graph.addVertice(vertice, vertice.getName());
    }

    public VerticeData getVerticeFrom( String graphName, String verticeName )
    {
        Graph graph = graphList.get(graphName);

        for(Object v : graph.getVerticesData())
        {
            VerticeData vertice = (VerticeData)v;
            if(vertice.getName().equals(verticeName))
            {
                return vertice;
            }
        }

        return null;
    }

    private EdgeData getEdgeFrom( String graphName, String originVerticeName, String destinationVerticeName )
    {
        Graph graph = graphList.get(graphName);
        VerticeData originVertice = getVerticeFrom( graphName, originVerticeName );
        VerticeData destinationVertice = getVerticeFrom( graphName, destinationVerticeName );
        
        ArrayList<EdgeData> edges = graph.getEdgesData();

        for( EdgeData edge: edges )
        {
            try
            {
                 if ( originVertice == graph.getInputVertice(edge) &&
                      destinationVertice == graph.getOutputVertice(edge) )
                 {
                     return edge;
                 }
            }
            catch( graph.exception.MissingEdge e )
            {
                //Não faz sentido ocerrer.
            }
        }
        return null;
    }

    public EdgeData getEdgeFrom( String graphName, String edgeName )
    {
        Graph graph = graphList.get(graphName);

        for(Object v : graph.getEdgesData())
        {
            EdgeData edge = (EdgeData)v;
            if(edge.getName().equals(edgeName))
            {
                return edge;
            }
        }

        return null;
    }
    
    public void addEdgeTo( String graphName, EdgeData edge, String originName, String destinationName )
            throws graph.exception.AlreadyHasEdge, graph.exception.MissingVertice
    {
        Graph graph = graphList.get(graphName);
        VerticeData origin = getVerticeFrom(graphName,originName);
        VerticeData destination = getVerticeFrom(graphName,destinationName);
        graph.addEdge(edge, origin, destination);
    }
    
    public void removeVerticeFrom( String graphName, String verticeName )
    {
        try
        {
            Graph graph = graphList.get( graphName );
            VerticeData vertice = getVerticeFrom( graphName, verticeName );
            graph.removeVertice(vertice);
        }
        catch (graph.exception.MissingVertice e)
        {
            System.out.println(e);
        }
    }
    
    public void removeEdgeFrom( String graphName, String originVerticeName, String destinationVerticeName )
    {
        try
        {
            Graph graph = graphList.get( graphName );
            EdgeData edge = getEdgeFrom( graphName, originVerticeName, destinationVerticeName );
            graph.removeEdge( edge );
        }
        catch ( graph.exception.MissingEdge e )
        {
            System.out.println(e);
        }
    }

    public void newGraph(String name)
    {
        Graph g = new Graph();
        graphList.put(name, g);
        lastName = name;

        //notifico as checklists para informar que um novo grafo foi adicionado
        for (ActionListener el : checklistObservers)
        {
            el.actionPerformed(null);
        }
    }
}
