package graph;

import java.io.Serializable;

class Edge<E extends EdgeData & Serializable > implements Serializable
{

    private E data;
    private Vertice inputVertice;
    private Vertice outputVertice;
    private Graph graph;

    Edge(E data, Vertice origin, Vertice destination, Graph graph)
    {
        this.data = data;
        this.inputVertice = origin;
        this.outputVertice = destination;
        this.graph = graph;
    }

    E getData()
    {
        return this.data;
    }

    double getCost()
    {
        return getData().getCost();
    }

    Graph getGraph()
    {
        return this.graph;
    }

    Vertice getInputVertice()
    {
        return this.inputVertice;
    }

    Vertice getOutputVertice()
    {
        return this.outputVertice;
    }

    void clear()
    {
        this.data = null;
        this.inputVertice = null;
        this.outputVertice = null;
        this.graph = null;
    }
}
