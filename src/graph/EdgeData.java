package graph;

/**
 * @title Trabalho 1 de Programação Orientada à Objetos - INF1636 - 2011.1
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 * 
 * A interface de EdgeData é necessária para qualquer classe que se deseje ser
 * utilizada como aresta de um grafo, já que implementa seu custo.
 */

public interface EdgeData
{
    /**
     * Deve receber um valor que será atribuído como custo de percorrimento de
     * uma aresta.
     * @return Custo.
     */
    double getCost();
    String getName();
}
