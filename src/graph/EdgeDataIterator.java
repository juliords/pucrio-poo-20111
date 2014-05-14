package graph;

/**
 * @title Trabalho 1 de Programação Orientada à Objetos - INF1636 - 2011.1
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 * 
 * A seguinte classe de iterador é feita para o percorrimento do conjunto de
 * arestas de um grafo. Qualquer mudança nesse conjunto invalida o estado do
 * iterador, que deve ser resetado para ser utilizado novamente.
 */

import java.io.Serializable;

public interface EdgeDataIterator<E extends EdgeData & Serializable>
{
    /**
     * O iterador retorna para a posição inicial do conjunto de arestas do grafo.
     * AS - O iterador está na posição inicial.
     *      O iterador está em estado válido.
     */
    void reset();

    /**
     * Verifica se o iterador pode ir para a próxima posição.
     * @return True caso tenha próximo.
     * @throws java.util.ConcurrentModificationException O iterador está em estado inválido.
     * INV - O iterador está em estado válido.
     */
    boolean hasNext() throws java.util.ConcurrentModificationException;

    /**
     * Pega o próximo elemento e anda com o iterador.
     * @return Próximo elemento.
     * @throws java.util.NoSuchElementException Não há próximo elemento
     * @throws java.util.ConcurrentModificationException O iterador está em estado inválido.
     * AE - Há próximo elemento do iterador.
     * INV - O iterador está em estado válido. 
     */
    E next() throws java.util.NoSuchElementException, java.util.ConcurrentModificationException;
}
