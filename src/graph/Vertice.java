package graph;

import java.io.Serializable;
import java.util.*;

class Vertice<V extends Serializable> implements Serializable
{

    private V data;
    private String uniqueName;
    private Graph graph;
    private ArrayList<Edge> inputEdges;
    private ArrayList<Edge> outputEdges;

    Vertice(V data, String uniqueName, Graph graph)
    {
        this.data = data;
        this.uniqueName = uniqueName; // atribuicao direta de string nao copia apenas a referencia
        this.graph = graph;
        this.inputEdges = new ArrayList<Edge>();
        this.outputEdges = new ArrayList<Edge>();
    }

    public ArrayList<Edge> getInputEdges()
    {
        return inputEdges;
    }

    public ArrayList<Edge> getOutputEdges()
    {
        return outputEdges;
    }

    V getData()
    {
        return this.data;
    }

    Graph getGraph()
    {
        return this.graph;
    }

    String getUniqueName()
    {
        return this.uniqueName;
    }

    void addInputEdge(Edge edge)
    {
        getInputEdges().add(edge);
    }

    void removeInputEdge(Edge edge)
    {
        getInputEdges().remove(edge);
    }

    void addOutputEdge(Edge edge)
    {
        getOutputEdges().add(edge);
    }

    void removeOutputEdge(Edge edge)
    {
        getOutputEdges().remove(edge);
    }

    ArrayList<Vertice> getOutputVertices()
    {
        ArrayList<Vertice> verticeList = new ArrayList<Vertice>();

        for (Edge e : this.outputEdges) {
            verticeList.add(e.getOutputVertice());
        }

        return verticeList;
    }

    ArrayList<Vertice> getInputVertices()
    {
        ArrayList<Vertice> verticeList = new ArrayList<Vertice>();

        for (Edge e : this.inputEdges) {
            verticeList.add(e.getInputVertice());
        }

        return verticeList;
    }

    void clear()
    {
        data = null;
        uniqueName = null;
        graph = null;

        inputEdges.clear();
        inputEdges = null;

        outputEdges.clear();
        outputEdges = null;
    }
}
