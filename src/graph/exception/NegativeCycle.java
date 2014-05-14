package graph.exception;

public class NegativeCycle extends GraphException
{
    @Override
    public String toString() { return "Foi encontrado um ciclo negativo.";}
}
