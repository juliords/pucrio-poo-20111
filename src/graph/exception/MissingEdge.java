package graph.exception;

public class MissingEdge extends EdgeException
{
    @Override
    public String toString() { return "Aresta nao encontrada no grafo";}
}
