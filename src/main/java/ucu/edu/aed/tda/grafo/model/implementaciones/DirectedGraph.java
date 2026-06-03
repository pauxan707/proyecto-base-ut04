package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

public class DirectedGraph<V, D> implements IDirectedIGraph<V, D> {
    protected HashMap<V, List<Edge<V, D>>> adyacencias;

    public DirectedGraph() {
        this.adyacencias = new HashMap<>();
    }

    /**
     * Agrega un vértice, y retorna true si efectivamente lo agrega
     */
    @Override
    public boolean agregarVertice(V vertex) {
        if (adyacencias.containsKey(vertex)) {
            return false;
        }
        adyacencias.put(vertex, new ArrayList<>());
        return true;
    }

    /**
     * Operación "Bulk",  agrega muchos vértices en la misma llamada
     */
    default void agregarVertices(Collection<V> vertices) {

    }


    @Override
    public V buscarVertice(Comparable<V> criterio) {
        for (V vertice : adyacencias.keySet()) {
            if (criterio.compareTo(vertice) == 0) {
                return vertice;
            }
        }
        return null;
    }

    /**
     * Agrega una arista al grafo, indicando con un booleano si la agregó
     */
    @Override
    public boolean agregarArista(V source, V target, D dato) {

        if (!adyacencias.containsKey(source) || !adyacencias.containsKey(target)) {
            return false;
        }

        if (existeArista(construirComparable(source), construirComparable(target))) {
            return false;
        }

        DirectedEdge<V, D> nuevaArista = new DirectedEdge<V,D>(source, target, dato);
        adyacencias.get(source).add(nuevaArista);

        return true;
    }

     /**
     * Elimina una arista del grafo
     */
    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {

        V src = buscarVertice(source);
        V trgt = buscarVertice(target);

        if (src == null || trgt == null) {
            return false;
        }

        Edge<V, D> arista = obtenerArista(source, target);

        if (arista == null) {
            return false;
        }

        adyacencias.get(src).remove(arista);
        return true;
    }
    
    /**
     * remueve un vértice del grafo, retorna true si el vértice fue efectivamente removido
     */
    @Override
    public boolean removerVertice(Comparable<V> criteria) {

    }

    
    /**
     * Conjunto de vértices (preferible devolver vista inmodificable).
     */
    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(adyacencias.keySet());
    }

    /**
     * Conjunto de aristas (preferible vista inmodificable).
     */
    @Override
    public Set<Edge<V, D>> aristas() {

        Set<Edge<V, D>> setAristas = new HashSet<>();

        for (List<Edge<V,D>> aristas : adyacencias.values()) {
            setAristas.addAll(aristas);
        }

        return Collections.unmodifiableSet(setAristas);
    }

     /**
     * ¿Existe el vértice v?
     */
    default boolean existeVertice(Comparable<V> criterio) {

    }

    /**
     * ¿Existe la arista (u -> v) en un grafo dirigido o (u,v) en uno no dirigido?
     */
    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {

        V src = buscarVertice(sourceCriteria);
        V trgt = buscarVertice(targetCriteria);

        if (src == null || trgt == null) {
            return false;
        }

        for (Edge<V, D> arista : adyacencias.get(src)) {
            if (targetCriteria.compareTo(arista.target()) == 0) {
                return true;
            }
        }
        return false;
    }

    
     /**
     * Retorna una arista que tiene un origen y destino source y target respectivamente
     */
    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {

        V src = buscarVertice(sourceCriteria);
        V trgt = buscarVertice(targetCriteria);

        if (src == null || trgt == null) {
            return null;
        }

        for  (Edge<V, D> arista : adyacencias.get(src)) {

            if (targetCriteria.compareTo(arista.target()) == 0) {
                return arista;
            }
        }

        return null;
    }

    default Edge<V, D> obtenerArista(V sourceCriteria, V targetCriteria) {
        return obtenerArista(construirComparable(sourceCriteria), construirComparable(targetCriteria));
    }


/**
     * Retorna todas las aristas que el vertex tiene como adyacentes.
     * En caso de que sea un grafo no dirigido, el método "source()"
     * referencia al vértice "verticeCriteria"
     */
    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
    }

    /**
     * Retorna la cantidad de vértices
     */
    default int cantidadDeVertices() {
        return vertices().size();
    }

    /**
     * Retorna la cantidad de artists
     */
    default int cantidadDeAristas() {
        return aristas().size();
    }
     
    /**
     * Retorna true si el grafo es conexo
     */
    @Override
    public boolean esConexo() {

        if (adyacencias.isEmpty()) {
            return true;
        }

        for (V vertice : adyacencias.keySet()) {

            Set<V> visitados = new HashSet<>();

            dfs(vertice, visitados);
            if (visitados.size() != adyacencias.size()) {
                return false;
            }
        }

        return true;
    }

    private void dfs(V vertice, Set<V> visitados) {

        visitados.add(vertice);

        for (Edge<V, D> arista : adyacencias.get(vertice)) {
            if (!visitados.contains(arista.target())) {
                dfs(arista.target(), visitados);
            }
        } 
    }
    
    /**
     * vacía el grafo
     */
    @Override
    public void vaciar() {
        
    }

    /**
     * retorna true si el grafo tiene ciclos
     */
    @Override
    public boolean tieneCiclos() {
        
        Set<V> visitados = new HashSet<>();
        Set<V> enProceso = new HashSet<>();

        for (V vertice : adyacencias.keySet()) {
            if (!visitados.contains(vertice)) {
                if (dfsCiclo(vertice, visitados, enProceso)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfsCiclo(V vertice, Set<V> visitados, Set<V> enProceso) {

        enProceso.add(vertice);

        for (Edge<V, D> arista: adyacencias.get(vertice)) {

            if (enProceso.contains(arista.target())) {
                return true;
            }
            
            if (!visitados.contains(arista.target())) {
                if (dfsCiclo(arista.target(), visitados, enProceso)) {
                    return true;
                }
            }
        }

        visitados.add(vertice);
        enProceso.remove(vertice);
        return false;
    }

     /** método utilitario para construir un comparable de un vértice cualquier utilizando equals */
    default Comparable<V> construirComparable(V vertice) {
        return x -> x.equals(vertice) ? 0 : 1;
    }

    /**
     * Sucesores (vecinos alcanzables por aristas salientes) de v.
     */
    @Override
    public Set<V> successors(Comparable<V> criteria) {

        V vertice = buscarVertice(criteria);
        Set<V> sucesores = new HashSet<>();

        if (vertice != null){
            for (Edge<V, D> arista : adyacencias.get(vertice)) {

                sucesores.add(arista.target());
            }
        }
        return sucesores;
    }

    /**
     * Predecesores (vecinos con aristas entrantes) de v.
     */
    @Override
    public Set<V> predecessors(Comparable<V> criteria) {

        V vertice = buscarVertice(criteria);
        Set<V> predecesores = new HashSet<>();

        if (vertice != null) {
            for (V v : adyacencias.keySet()) { 

                if (existeArista(construirComparable(v), criteria)) { 
                    predecesores.add(v);
                }

            }
        }
        return predecesores;
    }

    /**
     * Calcula la cantidad de vértices que salen de "v"
     */
    default int gradoDeSalida(Comparable<V> criteria) {
        return successors(criteria).size();
    }

    /**
     * Calcula la cantidad de vertices que "apuntan" a v
     */
    default int gradoDeEntrada(Comparable<V> criteria) {
        return predecessors(criteria).size();
    }

}