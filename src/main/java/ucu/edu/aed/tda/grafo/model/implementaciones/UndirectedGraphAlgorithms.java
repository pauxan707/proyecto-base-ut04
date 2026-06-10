package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.IUndirectedGraphAlgorithm;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class UndirectedGraphAlgorithms implements IUndirectedGraphAlgorithm {

     /**
     * Implementa el algoritmo Kruskal
     */
    @Override
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> kruskal(IUndirectedGraph<V, D> graph) {

        IUndirectedGraph<V,D> kruskal = new UndirectedGraph<>();
        
        Map<V,V> padres = new HashMap<>();

        for (V vertice : graph.vertices()) {
            padres.put(vertice, vertice);
            kruskal.agregarVertice(vertice);
        }

        List<Edge<V,D>> aristas = new ArrayList<>(graph.aristas());
        aristas.sort(new Comparator<Edge<V,D>>() {
            public int compare(Edge<V,D> a, Edge<V,D> b) {
                return Double.compare(a.dato().getWeight(), b.dato().getWeight());
            }
        });

        while (kruskal.cantidadDeAristas() < graph.cantidadDeVertices() - 1) {

            Edge<V,D> minEdge = aristas.removeFirst();
            if (minEdge == null) {
                return null;
            }

            if (!find(padres, minEdge.source()).equals(find(padres, minEdge.target()))) {
                union(padres, minEdge.source(), minEdge.target());
                kruskal.agregarArista(minEdge.source(), minEdge.target(), minEdge.dato());
            }
        }

        return kruskal;
    }

    private <V> V find(Map<V, V> padre, V vertice) {
        if (padre.get(vertice).equals(vertice)) {
            return vertice;
        }
        return find(padre, padre.get(vertice));
    }

    private <V> void union(Map<V, V> padre, V a, V b) {
        V raizA = find(padre, a);
        V raizB = find(padre, b);
        padre.put(raizB, raizA);
    }


    /**
     * ejecuta el algoritmo Prim sobre el grafo
     */
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> prim(IUndirectedGraph<V, D> graph, Comparable<V> source) {
        
        Collection<V> U = new HashSet<>();
        Collection<V> V = new HashSet<>();
        IUndirectedGraph<V, D> prim = new UndirectedGraph<>();

        V sourceVertex = graph.buscarVertice(source);
        if (sourceVertex == null) return null;

        U.add(sourceVertex);
        prim.agregarVertice(sourceVertex);

        for (V vertice : graph.vertices()) {
            if (!U.contains(vertice)) {
                V.add(vertice);
            }
        }

        while (!V.isEmpty()) {

            Edge<V, D> minEdge = searchMinEdge(graph, U, V);
            if (minEdge == null) return null;

            V nuevoVertice = null;
            if (V.contains(minEdge.target())) {
                nuevoVertice = minEdge.target();
            }
            else {
                nuevoVertice = minEdge.source();
            }

            V.remove(nuevoVertice);
            U.add(nuevoVertice);
            prim.agregarVertice(nuevoVertice);
            prim.agregarArista(minEdge.source(), minEdge.target(), minEdge.dato());
        }

        return prim;
    }
 


    /**
     * Retorna la mínima arista (u,v) del grafo "graph", tal que u está en U, y v está en V.
     * Este método es útil para implementar "Prim"
     */
    public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(IUndirectedGraph<V, D> graph, Collection<V> U, Collection<V> V) {
        Edge<V, D> minEdge = null;
        double minCosto = Double.MAX_VALUE;
        
        for (V verticeU : U) {
  
            for (Edge<V, D> arista : graph.adyacencias(graph.construirComparable(verticeU))) {
                
                V vecino = arista.target();
                
                if (V.contains(vecino)) {
                    double nuevoCosto = arista.dato().getWeight();
                    if (nuevoCosto < minCosto) {
                        minCosto = nuevoCosto;
                        minEdge = arista;
                    }
                }
            }
        }

        return minEdge;
    }

    /**
     * Implementa el algoritmo de búsqueda en amplitud
     */
    public <V, D> void bea(IUndirectedGraph<V, D> graph, Consumer<V> consumer) {
        
        Set<V> visitados = new HashSet<>();

        for (V vertice : graph.vertices()) {
            bea(graph, consumer, graph.construirComparable(vertice), visitados);
        }
    }

    private <V,D> void bea(IUndirectedGraph<V, D> graph, Consumer<V> consumer, Comparable<V> sourceCriteria, Set<V> visitados) {

        V source = graph.buscarVertice(sourceCriteria);
        if (source == null) {
            return;
        }

        Queue<V> cola = new LinkedList<>();
        cola.add(source);
        visitados.add(source);

        while (!cola.isEmpty()) {
            V actual = cola.poll();
            consumer.accept(actual);

            for (Edge<V,D> arista : graph.adyacencias(graph.construirComparable(actual))) {
                
                V adyacente = arista.target();
                if (!visitados.contains(adyacente)) {
                    visitados.add(adyacente);
                    cola.add(adyacente);
                }
            }
        }
    }
}
