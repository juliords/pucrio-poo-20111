package graph;

/**
 * @title Trabalho 1 de Programação Orientada à Objetos - INF1636 - 2011.1
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 * 
 * A seguinte abstração de grafo permite a criação e manipulação, além da
 * gravação e recuperação, de dados relacionados através de vértices e arestas
 * na forma de grafo direcionado. Como INVARIANTE de seus métodos estão as
 * assertivas estruturais do grafo, a saber: O grafo é formado por um conjunto
 * (n>=0) de vértices e arestas. Cada vértice deve ser associado por construção
 * por um nome(String) que o identifique univocamente no grafo; Cada aresta deve
 * ter um vértice de entrada e outro de saída, todos pertencentes ao mesmo
 * grafo; A partir de cada aresta deve ser possível se obter um determinado
 * custo de percorrimento.
 */

import java.io.Serializable;
import java.util.*;

import graph.exception.*;

public interface GraphInterface< V extends Serializable,
                                 E extends EdgeData >
                               extends Cloneable, Serializable
{

    /**
     * Acrescenta um novo vértice ao grafo.
     * @param data Objeto que será associado ao vértice.
     * @param uniqueName Nome único no grafo que representará o vértice.
     * @throws AlreadyHasVertice O objeto já está associado a um vértice do grafo.
     * @throws NotUniqueName O nome já representa um vértice.
     * AE - O objeto não está associado a um vértice do grafo.
     *      O nome não representa nenhum vértice do grafo.
     * AS - O conjunto de vértices do grafo cresceu em uma unidade.
     *      O novo vértice está associado ao objeto de entrada e ao nome univoco.
     */
    void addVertice(V data, String uniqueName) throws AlreadyHasVertice,
                                                      NotUniqueName;
    /**
     * Acrescenta uma nova aresta ao grafo.
     * @param data Objeto que será associado à aresta.
     * @param origin Objeto do vértice de origem da aresta.
     * @param destination Objeto do vértice de destino da aresta.
     * @throws MissingVertice Vértices de entrada e/ou saída não estão no grafo.
     * @throws AlreadyHasEdge O objeto já está associado a uma aresta do grafo.
     * AE - O objeto não está associado a uma aresta do grafo.
     *      Os objetos de origem e destino estão associados à vértices do grafo.
     * AS - O conjunto de arestas do grafo cresceu em uma unidade.
     *      A nova aresta está associado ao objeto de entrada e dele pode ser
     *      obtido um custo de percorrimento.
     */
    void addEdge(E data, V origin, V destination) throws MissingVertice,
                                                         AlreadyHasEdge;
    /**
     * Verifica se um objeto está associado a um vértice do grafo.
     * @param data Objeto a ser verificado.
     * @return True caso esteja no grafo.
     */
    boolean hasVertice(V data);

    /**
     * Verifica se um objeto está associado a uma aresta do grafo.
     * @param data Objeto a ser verificado.
     * @return True caso esteja no grafo.
     */
    boolean hasEdge(E data);

    /**
     * Conta os vértices do grafo.
     * @return Número de vértices
     */
    int sizeInVertices();

    /**
     * Conta as arestas do grafo.
     * @return Número de arestas
     */
    int sizeInEdges();

    /**
     * Retira um vértice do grafo.
     * @param verticeData Objeto associado ao vértice a ser removido.
     * @throws MissingVertice O objeto não está associado ao grafo.
     * AE - O objeto está associado a um vértice do grafo.
     * AS - O conjunto de vértices do grafo não possui elemento com o objeto.
     *      Qualquer aresta que tem como origem ou destino esse vértice também
     *      é removida do grafo.
     */
    void removeVertice(V verticeData) throws MissingVertice;

    /**
     * Retira uma aresta do grafo.
     * @param edgeData Objeto associado à aresta a ser removida.
     * @throws MissingEdge O objeto não está associado ao grafo.
     * AE - O objeto está associado a uma aresta do grafo.
     * AS - O conjunto de arestas do grafo não possui elemento com o objeto.
     */
    void removeEdge(E edgeData) throws MissingEdge;

    /**
     * Remove todos os vértices e arestas do grafo.
     * AS - O grafo não tem vértice e arestas, não tendo objetos associados a eles.
     */
    void clear();

    /**
     * Remove todas as arestas do grafo.
     * AS - O grafo não tem arestas, não tendo objetos associados a elas.
     */
    void clearEdges();

    /**
     * Pega uma lista com todos os objetos de vértice do grafo.
     * @return Uma nova lista com os objetos de vértice.
     */
    ArrayList<V> getVerticesData();

    /**
     * Pega uma lista com todos os objetos de aresta do grafo.
     * @return Uma nova lista com os objetos de aresta.
     */
    ArrayList<E> getEdgesData();

    /**
     * Pega uma lista com todas arestas que tem como destino certo vértice.
     * @param destination Objeto do vértice de destino.
     * @return Uma nova lista com os objetos de aresta.
     * @throws MissingVertice O objeto de vértice não está associado ao grafo.
     * INV - O objeto de vértice está associado a um vértice do grafo.
     */
    ArrayList<E> getInputEdges(V destination) throws MissingVertice;

    /**
     * Pega uma lista com todas arestas que tem como origem certo vértice.
     * @param origin Objeto do vértice de origem.
     * @return Uma nova lista com os objetos de aresta.
     * @throws MissingVertice O objeto de vértice não está associado ao grafo.
     * INV - O objeto de vértice está associado a um vértice do grafo.
     */
    ArrayList<E> getOutputEdges(V origin) throws MissingVertice;

    /**
     * Pega uma lista com todos vértices que tem como destino certo vértice.
     * @param destination Objeto do vértice de destino.
     * @return Uma nova lista com os objetos de vértice.
     * @throws MissingVertice O objeto de destino não está associado ao grafo.
     * INV - O objeto de destino está associado a um vértice do grafo.
     */
    ArrayList<V> getInputVertices(V destination) throws MissingVertice;

    /**
     * Pega uma lista com todos vértices que tem como origem certo vértice.
     * @param origin Objeto do vértice de destino.
     * @return Uma nova lista com os objetos de vértice.
     * @throws MissingVertice O objeto de origem não está associado ao grafo.
     * INV - O objeto de origem está associado a um vértice do grafo.
     */
    ArrayList<V> getOutputVertices(V origin) throws MissingVertice;

    /**
     * Pega o objeto do vértice que é entrada de certa aresta.
     * @param edgeData Objeto de aresta.
     * @return Objeto do vértice de entrada
     * @throws MissingEdge  O objeto de aresta não está associado ao grafo.
     * INV - O objeto de aresta está associado a uma aresta do grafo.
     */
    V getInputVertice(E edgeData) throws MissingEdge;

    /**
     * Pega o objeto do vértice que é saída de certa aresta.
     * @param edgeData Objeto de aresta.
     * @return Objeto do vértice de saída
     * @throws MissingEdge  O objeto de aresta não está associado ao grafo.
     * INV - O objeto de aresta está associado a uma aresta do grafo.
     */
    V getOutputVertice(E edgeData) throws MissingEdge;

    /**
     * Calcula o caminho de menor custo entre dois vértices através do custo
     * das arestas.
     * @param origin Objeto do vértice de origem do caminho.
     * @param destination Objeto do vértice de destino do caminho.
     * @return Uma nova lista com os objetos de arestas em ordem que devem ser
     * percorridas. Caso os vértices sejam desconexos retorna uma lista vazia.
     * @throws MissingVertice Algum objeto de vértice não está associado ao grafo.
     * @throws NegativeCycle Há um ciclo negativo no grafo.
     * INV - O grafo não apresenta ciclos negativos.
     *       Os objetos de vértice estão associado ao grafo.
     */
    ArrayList<E> computeShortestPath(V origin, V destination)
                 throws MissingVertice, NegativeCycle;

    /**
     * Obtêm um iterador para percorrer os objetos de vértice do grafo.
     * @return O iterador de vértices.
     */
    VerticeDataIterator verticeIterator();

    /**
     * Obtêm um iterador para percorrer os objetos de aresta do grafo.
     * @return O iterador de arestas.
     */
    EdgeDataIterator edgeIterator();

    /**
     * Verifica se certo nome(String) já tem representação de um vértice no grafo.
     * @param name Nome a ser testado.
     * @return True caso o nome esteja livre para ser utilizado.
     */
    boolean isNameUnique(String name);

    /**
     * Grava a estrutura de grafo com seu conteúdo em arquivo.
     * @param filePath Arquivo de destino.
     * @throws CannotCreateFile Não foi possível criar arquivo.
     * @throws java.io.IOException Problema encontrado ao escrever arquivo.
     * AS - O arquivo foi criado e escrito com os dados do grafo.
     */
    void save(String filePath) throws CannotCreateFile,
                                      java.io.IOException;
}
