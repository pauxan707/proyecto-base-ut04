package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

public class DirectedGraph<V, D> implements IDirectedIGraph<V, D> {
    public final Set<V> vertices = new HashSet<>();
    public final Set<Edge<V, D>> aristas = new HashSet<>();
    /**
     * Agrega un vértice, y retorna true si efectivamente lo agrega
     */
    public boolean agregarVertice(V vertex){
        return vertices.add(vertex);
    }

    /**
     * Operación "Bulk",  agrega muchos vértices en la misma llamada
     */
    default void agregarVertices(Collection<V> vertices) {
        vertices.forEach(this::agregarVertice);
    }


    public V buscarVertice(Comparable<V> criterio){
        return vertices.stream().filter(v -> criterio.compareTo(v) == 0).findFirst().orElse(null);
    }

    /**
     * Agrega una arista al grafo, indicando con un booleano si la agregó
     */
    public boolean agregarArista(V source, V target, D dato){
        if (!vertices.contains(source) || !vertices.contains(target)) {
            return false;
        }
        return aristas.add(new DirectedEdge<>(source, target, dato));
    }

    /**
     * Elimina una arista del grafo
     */
    boolean eliminarArista(Comparable<V> source, Comparable<V> target);

    /**
     * remueve un vértice del grafo, retorna true si el vértice fue efectivamente removido
     */
    boolean removerVertice(Comparable<V> criteria);

    /**
     * Conjunto de vértices (preferible devolver vista inmodificable).
     */
    @Override
    public Set<V> vertices(){
        return Collections.unmodifiableSet(vertices);
    }

    /**
     * Conjunto de aristas (preferible vista inmodificable).
     */
    @Override
    public Set<Edge<V, D>> aristas(){
        return Collections.unmodifiableSet(aristas);
    }

    /**
     * ¿Existe el vértice v?
     */
    default boolean existeVertice(Comparable<V> criterio) {
        return vertices().stream().anyMatch(x -> criterio.compareTo(x) == 0);
    }

    /**
     * ¿Existe la arista (u -> v) en un grafo dirigido o (u,v) en uno no dirigido?
     */
    boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria);

    /**
     * Retorna una arista que tiene un origen y destino source y target respectivamente
     */
    Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria);

    default Edge<V, D> obtenerArista(V sourceCriteria, V targetCriteria) {
        return obtenerArista(construirComparable(sourceCriteria), construirComparable(targetCriteria));
    }

    /**
     * Retorna todas las aristas que el vertex tiene como adyacentes.
     * En caso de que sea un grafo no dirigido, el método "source()"
     * referencia al vértice "verticeCriteria"
     */
    List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria);

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
    boolean esConexo();

    /**
     * vacía el grafo
     */
    public void vaciar(){
        vertices.clear();
        aristas.clear();
    }

    /**
     * retorna true si el grafo tiene ciclos
     */
    boolean tieneCiclos();

/** método utilitario para construir un comparable de un vértice cualquier utilizando equals */
    default Comparable<V> construirComparable(V vertice) {
        return x -> x.equals(vertice) ? 0 : 1;
    }

    Set<V> successors(Comparable<V> criteria);

    /**
     * Predecesores (vecinos con aristas entrantes) de v.
     */
    Set<V> predecessors(Comparable<V> criteria);

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