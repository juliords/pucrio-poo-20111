package graph;

import graph.exception.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.Serializable;

public class Graph< V extends VerticeData & Serializable, E extends EdgeData & Serializable >
        implements GraphInterface<V, E>
{

    private ArrayList<Edge<E>> edgeList;
    private ArrayList<Vertice<V>> verticeList;
    private HashMap<V, Vertice<V>> hashVertice;
    private HashMap<E, Edge<E>> hashEdge;
    private HashMap<String, Vertice<V>> hashUniqueName;
    private int verticeStateCounter;
    private int edgeStateCounter;
    private final static double INF = Double.MAX_VALUE;

    public Graph()
    {
        this.edgeList = new ArrayList<Edge<E>>();
        this.verticeList = new ArrayList<Vertice<V>>();
        this.hashVertice = new HashMap<V, Vertice<V>>();
        this.hashEdge = new HashMap<E, Edge<E>>();
        this.hashUniqueName = new HashMap<String, Vertice<V>>();
        verticeStateCounter = 0;
        edgeStateCounter = 0;
    }

    @Override
    public ArrayList<E> computeShortestPath(V origin, V destination) throws MissingVertice, NegativeCycle
    {
        if ( !hasVertice(origin) || !hasVertice(destination) ) throw new MissingVertice();

        return bellmanford(getVerticeByVerticeData(origin), getVerticeByVerticeData(destination));
    }

    private ArrayList<E> bellmanford(Vertice source, Vertice destination) throws NegativeCycle
    {
        ArrayList<Vertice> path = new ArrayList<Vertice>();
        double[] distance = new double[sizeInVertices()];

        Arrays.fill(distance, INF);
        distance[verticeList.indexOf(source)] = 0;

        ArrayList<Vertice> parentNode = new ArrayList<Vertice>();
        for(int i=0; i<verticeList.size(); i++)
            parentNode.add(null);
        
        parentNode.add(verticeList.indexOf(source), source);

        for (int i=0; i<verticeList.size(); i++) {
            for (Edge tmpEdge : edgeList) {
                if (distance[verticeList.indexOf(tmpEdge.getInputVertice())] == INF) {
                    continue;
                }

                double newDistance = distance[verticeList.indexOf(tmpEdge.getInputVertice())] + tmpEdge.getCost();

                if (newDistance < distance[verticeList.indexOf(tmpEdge.getOutputVertice())]) {
                    distance[verticeList.indexOf(tmpEdge.getOutputVertice())] = newDistance;
                    parentNode.set(verticeList.indexOf(tmpEdge.getOutputVertice()), tmpEdge.getInputVertice());
                }
            }
        }

        for (Edge tmpEdge : edgeList) {
            if (distance[verticeList.indexOf(tmpEdge.getInputVertice())] != INF
                    && distance[verticeList.indexOf(tmpEdge.getOutputVertice())]
                    > distance[verticeList.indexOf(tmpEdge.getInputVertice())] + tmpEdge.getCost()) {
                // Caso encontre um ciclo negativo, não é possível proceder com o algoritmo.
                throw new NegativeCycle();
            }
        }

        for (int i = 0; i < distance.length; i++) {
            if (distance[i] == INF && verticeList.indexOf(destination) == i) {
                // Caso em que é impossivel chegar no destino, não existindo um caminho da origem a ele.
                return new ArrayList<E>();
            }
        }

        createVertPath(source, destination, parentNode, path);
        return createEdgePath(path);
    }

    private void createVertPath(Vertice source, Vertice destination, ArrayList<Vertice> parentNode, ArrayList<Vertice> path)
    {
        if (source != destination) {
            createVertPath(source, parentNode.get(verticeList.indexOf(destination)), parentNode, path);
        } 
        path.add(destination);
    }

    private ArrayList<E> createEdgePath(ArrayList<Vertice> path)
    {
        double max_cost = INF;
        ArrayList<E> ePath = new ArrayList<E>();
        ArrayList<Edge> lstEdge = new ArrayList<Edge>();
        Edge<E> minEdge = null;

        for(int i=0; i<path.size()-1; i++)
        {
            lstEdge = path.get(i).getOutputEdges();
            for(Edge e : lstEdge)
            {
                if(e.getOutputVertice() == path.get(i+1) && e.getCost() < max_cost)
                {
                    minEdge = e;
                    max_cost = e.getCost();
                }
            }
            ePath.add(minEdge.getData());
            max_cost = INF;
        }

        return ePath;
    }

    private ArrayList<Edge<E>> getEdgeList()
    {
        return edgeList;
    }

    private HashMap<E, Edge<E>> getHashEdge()
    {
        return hashEdge;
    }

    private HashMap<V, Vertice<V>> getHashVertice()
    {
        return hashVertice;
    }

    private HashMap<String, Vertice<V>> getHashUniqueName()
    {
        return hashUniqueName;
    }

    private ArrayList<Vertice<V>> getVerticeList()
    {
        return verticeList;
    }

    private Vertice<V> getVerticeByVerticeData(V data)
    {
        return getHashVertice().get(data);
    }

    private Edge<E> getEdgeByEdgeData(E data)
    {
        return getHashEdge().get(data);
    }

    @Override
    public void addVertice(V data, String uniqueName) throws AlreadyHasVertice,
            NotUniqueName
    {
        if (getVerticeByVerticeData(data) != null) {
            throw new AlreadyHasVertice();
        }
        if ( ! isNameUnique(uniqueName)) {
            throw new NotUniqueName();
        }

        Vertice newVertice = new Vertice<V>(data, uniqueName, this);

        getVerticeList().add(newVertice);
        invalidateVerticeIterators();

        getHashVertice().put(data, newVertice);
        getHashUniqueName().put(uniqueName, newVertice);
    }

    @Override
    public void addEdge(E data, V origin, V destination) throws MissingVertice,
            AlreadyHasEdge
    {
        Vertice originVertice = getVerticeByVerticeData(origin);
        Vertice destinationVertice = getVerticeByVerticeData(destination);

        if (originVertice == null || destinationVertice == null) {
            throw new MissingVertice();
        }

        if (getEdgeByEdgeData(data) != null) {
            throw new AlreadyHasEdge();
        }

        Edge<E> newEdge = new Edge<E>(data, originVertice, destinationVertice, this);

        getHashEdge().put(data, newEdge);
        getEdgeList().add(newEdge);
        invalidateEdgeIterators();

        originVertice.addOutputEdge(newEdge);
        destinationVertice.addInputEdge(newEdge);
    }

    @Override
    public boolean hasVertice(V data)
    {
        return (getVerticeByVerticeData(data) != null);
    }

    @Override
    public boolean hasEdge(E data)
    {
        return (getEdgeByEdgeData(data) != null);
    }

    private void removeVerticeInt(Vertice<V> vertice)
    {
        ArrayList<Edge<E>> inputEdgeList = (ArrayList<Edge<E>>)vertice.getInputEdges().clone();
        ArrayList<Edge<E>> outputEdgeList = (ArrayList<Edge<E>>)vertice.getOutputEdges().clone();

        for (Edge<E> e : inputEdgeList) {
            removeEdgeInt(e);
        }
        for (Edge<E> e : outputEdgeList) {
            removeEdgeInt(e);
        }

        getHashUniqueName().remove(vertice.getUniqueName());
        getHashVertice().remove(vertice.getData());
        getVerticeList().remove(vertice);
        invalidateVerticeIterators();

        vertice.clear();
    }

    @Override
    public void removeVertice(V verticeData) throws MissingVertice
    {
        Vertice<V> vertice = getVerticeByVerticeData(verticeData);
        if (vertice == null) {
            throw new MissingVertice();
        }
        removeVerticeInt(vertice);
    }

    private void removeEdgeInt(Edge<E> edge)
    {
        Vertice<V> inputVertice = edge.getInputVertice();
        Vertice<V> outputVertice = edge.getOutputVertice();

        inputVertice.removeOutputEdge(edge);
        outputVertice.removeInputEdge(edge);

        getEdgeList().remove(edge);
        getHashEdge().remove(edge.getData());
        invalidateEdgeIterators();

        edge.clear();
    }

    @Override
    public void removeEdge(E edgeData) throws MissingEdge
    {
        Edge<E> edge = getEdgeByEdgeData(edgeData);
        if (edge == null) {
            throw new MissingEdge();
        }
        removeEdgeInt(edge);
    }

    @Override
    public int sizeInVertices()
    {
        return getVerticeList().size();
    }

    @Override
    public int sizeInEdges()
    {
        return getEdgeList().size();
    }

    @Override
    public void clear()
    {
        ArrayList<Vertice<V>> array = (ArrayList<Vertice<V>>)getVerticeList().clone();

        for (Vertice<V> v : array) {
            removeVerticeInt(v);
        }

        // so pra garantir
        this.edgeList.clear();
        this.verticeList.clear();
        this.hashVertice.clear();
        this.hashEdge.clear();
        this.hashUniqueName.clear();
    }

    @Override
    public void clearEdges()
    {
        ArrayList<Edge<E>> array = (ArrayList<Edge<E>>)getEdgeList().clone();

        for (Edge<E> e : array) {
            removeEdgeInt(e);
        }

        // so pra garantir
        this.edgeList.clear();
        this.hashEdge.clear();
    }

    @Override
    public ArrayList<V> getVerticesData()
    {
        ArrayList<V> array = new ArrayList<V>();

        for (Vertice<V> data : getVerticeList()) {
            array.add(data.getData());
        }

        return array;
    }

    @Override
    public ArrayList<E> getEdgesData()
    {
        ArrayList<E> array = new ArrayList<E>();

        for (Edge<E> data : getEdgeList()) {
            array.add(data.getData());
        }

        return array;
    }

    @Override
    public ArrayList<E> getInputEdges(V destination) throws MissingVertice
    {
        ArrayList<E> arrayData = new ArrayList<E>();

        Vertice vertice = getVerticeByVerticeData(destination);
        if (vertice == null) {
            throw new MissingVertice();
        }

        ArrayList<Edge> arrayEdge = vertice.getInputEdges();

        for (Edge<E> e : arrayEdge) {
            arrayData.add(e.getData());
        }

        return arrayData;
    }

    @Override
    public ArrayList<E> getOutputEdges(V origin) throws MissingVertice
    {
        ArrayList<E> arrayData = new ArrayList<E>();

        Vertice vertice = getVerticeByVerticeData(origin);
        if (vertice == null) {
            throw new MissingVertice();
        }

        ArrayList<Edge> arrayEdge = vertice.getOutputEdges();

        for (Edge<E> e : arrayEdge) {
            arrayData.add(e.getData());
        }

        return arrayData;
    }

    @Override
    public ArrayList<V> getInputVertices(V destination) throws MissingVertice
    {
        ArrayList<V> arrayData = new ArrayList<V>();

        Vertice destinationVertice = getVerticeByVerticeData(destination);
        if (destinationVertice == null) {
            throw new MissingVertice();
        }

        ArrayList<Vertice> arrayVertice = destinationVertice.getInputVertices();

        for (Vertice<V> v : arrayVertice) {
            arrayData.add(v.getData());
        }

        return arrayData;
    }

    @Override
    public ArrayList<V> getOutputVertices(V origin) throws MissingVertice
    {
        ArrayList<V> arrayData = new ArrayList<V>();

        Vertice<V> originVertice = getVerticeByVerticeData(origin);
        if (originVertice == null) {
            throw new MissingVertice();
        }

        ArrayList<Vertice> arrayVertice = originVertice.getOutputVertices();

        for (Vertice<V> v : arrayVertice) {
            arrayData.add(v.getData());
        }

        return arrayData;
    }

    @Override
    public V getInputVertice(E edgeData) throws MissingEdge
    {
        Edge edge = getEdgeByEdgeData(edgeData);
        if (edge == null) {
            throw new MissingEdge();
        }

        Vertice<V> vertice = edge.getInputVertice();
        return vertice.getData();
    }

    @Override
    public V getOutputVertice(E edgeData) throws MissingEdge
    {
        Edge edge = getEdgeByEdgeData(edgeData);
        if (edge == null) {
            throw new MissingEdge();
        }

        Vertice<V> vertice = edge.getOutputVertice();
        return vertice.getData();
    }

    @Override
    public boolean isNameUnique(String name)
    {
        return (getHashUniqueName().get(name) == null);
    }

    @Override
    public Graph<V, E> clone()
    {
        Graph<V, E> newGraph = new Graph<V, E>();

        newGraph.verticeStateCounter = this.verticeStateCounter;
        newGraph.edgeStateCounter = this.edgeStateCounter;

        for (Vertice<V> v : getVerticeList()) {
            try {
                newGraph.addVertice(v.getData(), v.getUniqueName());
            } catch (Exception ex) // nunca deve cair aqui
            {
                System.out.println(ex);
            }
        }

        for (Edge<E> e : getEdgeList()) {
            Vertice<V> origin = e.getInputVertice();
            Vertice<V> destination = e.getOutputVertice();

            try {
                newGraph.addEdge(e.getData(), origin.getData(), destination.getData());
            } catch (Exception ex) // nunca deve cair aqui
            {
                System.out.println(ex);
            }

        }

        return newGraph;
    }

    @Override
    public VerticeDataIterator verticeIterator()
    {
        VerticeIterator iterator = new VerticeIterator();
        return iterator;
    }

    @Override
    public EdgeDataIterator edgeIterator()
    {
        EdgeIterator iterator = new EdgeIterator();
        return iterator;
    }

    private void invalidateVerticeIterators()
    {
        verticeStateCounter++;
    }

    private void invalidateEdgeIterators()
    {
        edgeStateCounter++;
    }

    private class VerticeIterator implements graph.VerticeDataIterator
    {

        private int current;
        private int state_version;

        private VerticeIterator()
        {
            current = 0;
            state_version = verticeStateCounter;
        }

        private boolean isValid()
        {
            return state_version == verticeStateCounter;
        }

        public void reset()
        {
            current = 0;
            state_version = verticeStateCounter;
        }

        public boolean hasNext() throws java.util.ConcurrentModificationException
        {
            if (!isValid()) {
                throw new java.util.ConcurrentModificationException();
            }
            return sizeInVertices() > current;
        }

        public V next() throws java.util.NoSuchElementException, java.util.ConcurrentModificationException
        {
            if (!isValid()) {
                throw new java.util.ConcurrentModificationException();
            }

            if (hasNext()) {
                Vertice<V> currentVertice = getVerticeList().get(current);
                current++;
                return currentVertice.getData();

            } else {
                throw new java.util.NoSuchElementException();
            }
        }
    }

    private class EdgeIterator implements graph.EdgeDataIterator
    {

        private int current;
        private int state_version;

        private EdgeIterator()
        {
            current = 0;
            state_version = edgeStateCounter;
        }

        private boolean isValid()
        {
            return state_version == edgeStateCounter;
        }

        public void reset()
        {
            current = 0;
            state_version = edgeStateCounter;
        }

        public boolean hasNext() throws java.util.ConcurrentModificationException
        {
            if (!isValid()) {
                throw new java.util.ConcurrentModificationException();
            }
            return sizeInEdges() > current;
        }

        public E next() throws java.util.NoSuchElementException, java.util.ConcurrentModificationException
        {
            if (!isValid()) {
                throw new java.util.ConcurrentModificationException();
            }

            if (hasNext()) {
                Edge<E> currentEdge = getEdgeList().get(current);
                current++;
                return currentEdge.getData();

            } else {
                throw new java.util.NoSuchElementException();
            }
        }
    }

    @Override
    public void save(String filePath) throws CannotCreateFile, java.io.IOException
    {
        GraphPersist.save(this, filePath);
    }

    public static Graph load(String filePath)
            throws java.io.FileNotFoundException,
            java.io.IOException, java.lang.ClassNotFoundException
    {
        return (Graph)GraphPersist.load(filePath);
    }
}
