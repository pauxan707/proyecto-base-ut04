package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.Collection;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.IUndirectedGraphAlgorithm;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class UndirectedGraphAlgorithms implements IUndirectedGraphAlgorithm {

     /**
     * Implementa el algoritmo Kruskal
     */
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> kruskal(IUndirectedGraph<V, D> graph)
    {
        return null;
    }

    /**
     * ejecuta el algoritmo Prim sobre el grafo
     */
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> prim(IUndirectedGraph<V, D> graph, Comparable<V> source)
    {
        return null;
    }
 


    /**
     * Retorna la mínima arista (u,v) del grafo "graph", tal que u está en U, y v está en V.
     * Este método es útil para implementar "Prim"
     */
    public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(IUndirectedGraph<V, D> graph, Collection<V> U, Collection<V> V)
    {
        return null;
    }

    /**
     * Implementa el algoritmo de búsqueda en amplitud
     */
    public <V, D> void bea(IUndirectedGraph<V, D> graph, Consumer<V> consumer)
    {
        
    }
    
}
