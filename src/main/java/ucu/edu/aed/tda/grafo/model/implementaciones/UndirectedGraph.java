package ucu.edu.aed.tda.grafo.model.implementaciones;

import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.UndirectedEdge;
import java.util.*;

/**
 * Implementacion de grafo no dirigido con lista de adyacencia.
 * Las aristas se almacenan en ambos sentidos para facilitar la busqueda por adyacencias, 
 * pero el conjunto mantiene una copia lógica por par
 */
public class UndirectedGraph <V,D> implements IUndirectedGraph<V, D> {
    protected HashMap<V, List<Edge<V, D>>> adyacencias = new HashMap<>();

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
     * remueve un vértice del grafo, retorna true si el vértice fue efectivamente removido
     */
    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        V vertice = buscarVertice(criteria);

        if (vertice == null) {
            return false;
        }
        /**
         * Eliminar aristas incidentes en todos los vecinos
         */
        for (Edge<V, D> arista : new ArrayList<>(adyacencias.get(vertice))) {
            V otro = arista.target().equals(vertice) ? arista.source() : arista.target();
            adyacencias.get(otro).removeIf(a ->
                (a.source().equals(vertice) || a.target().equals(vertice)));
        }
        adyacencias.remove(vertice);
        return true;
    }

    
    /**
     * Conjunto de vértices (preferible devolver vista inmodificable).
     */
    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(adyacencias.keySet());
    }

    /**
     * Agrega una arista al grafo, indicando con un booleano si la agregó
     */
    @Override
    public boolean agregarArista (V source, V target, D dato) {
        if (!adyacencias.containsKey(source) || !adyacencias.containsKey(target)) {
            return false;
        }
        
        if (existeArista(construirComparable(source), construirComparable(target))) {
            return false;
        }

        UndirectedEdge<V, D> arista = new UndirectedEdge<>(source, target, dato);
        adyacencias.get(source).add(arista);
        if (!source.equals(target)) {
            /**
             * Para que adyacencias (target) tambien la devuelva, guardamos la arista invertida, 
             * siendo el mismo objeto lógico
             */
            adyacencias.get(target).add(new UndirectedEdge<>(target,source, dato));
        }
        return true;
    }

    /**
     * Elimina una arista del grafo
     */
    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        V src = buscarVertice(source);
        V trg = buscarVertice(target);
        if (src == null || trg == null) {
            return false;
        }

        boolean eliminadoEnOrden = adyacencias.get(src).removeIf(arista -> target.compareTo((otherEnd(arista,src))) == 0);
        boolean eliminadoEnDestino =adyacencias.get(trg).removeIf(arista -> source.compareTo((otherEnd(arista,trg))) == 0);
        return eliminadoEnOrden || eliminadoEnDestino;
    }

    /**
     * Devuelve el extremo opuesto de la arista respecto al vértice dado
     */
    private V otherEnd(Edge<V, D> arista, V vertice) {
        return arista.source().equals(vertice) ? arista.target() : arista.source();
    }

    /**
     * Conjunto de aristas (preferible vista inmodificable).
     */
    @Override 
    public Set<Edge<V,D>> aristas() {
        /**
         * Devuelve solo una arista por par, la que tiene source <= target segun hashCode
         */
        Set<Edge<V, D>> result = new LinkedHashSet<>();
        for (List<Edge<V, D>> lista : adyacencias.values()) {
            for (Edge<V, D> arista : lista) {
                /**
                 * UndirectedEdge.equals es simetrico, asi que Set descarta duplicados
                 */
                result.add(arista);
            }
        }
        return Collections.unmodifiableSet(result);
    }

    /**
     * ¿Existe la arista (u,v) en un grafo no dirigido?
     */
    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {

        V src = buscarVertice(sourceCriteria);
        if (src == null) {
            return false;
        }

        for (Edge<V, D> arista : adyacencias.get(src)) {
            if (targetCriteria.compareTo(otherEnd(arista, src)) == 0) {
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

        if (src == null) {
            return null;
        }

        for (Edge<V, D> arista : adyacencias.get(src)) {
            if (targetCriteria.compareTo(otherEnd(arista, src)) == 0) {
                return arista;
            }
        }
        return null;
    }

    /**
     *  Retorna todas las aristas que el vertex tiene como adyacentes.
     * En caso de que sea un grafo no dirigido, el método "source()"
     * referencia al vértice "verticeCriteria"
     */
    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        V vertice = buscarVertice(verticeCriteria);
        if (vertice == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(adyacencias.get(vertice));
    }

    /**
     * Retorna true si el grafo es conexo
     */
    @Override
    public boolean esConexo() {
        if (adyacencias.isEmpty()) {
            return true;
        }
        Set<V> visitados = new HashSet<>();
        V inicio = adyacencias.keySet().iterator().next();
        dfs(inicio, visitados);
        return visitados.size() == adyacencias.size();
    }

    private void dfs(V v, Set<V> visitado) {
        visitado.add(v);
        for (Edge<V, D> arista : adyacencias.get(v)) {
            V vecino = otherEnd(arista, v);
            if (!visitado.contains(vecino)) {
                dfs(vecino, visitado);
            }
        }
    }

    /**
     * retorna true si el grafo tiene ciclos
     */
    @Override
    public boolean tieneCiclos() {
        Set<V> visitados = new HashSet<>();
        for (V v : adyacencias.keySet()) {
            if (!visitados.contains(v) && dfsCiclo(v, null, visitados)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfsCiclo(V v, V padre, Set<V> visitado) {
        visitado.add(v);
        for(Edge<V , D> arista : adyacencias.get(v)) {
            V vecino = otherEnd(arista, v);
            if (!visitado.contains(vecino)) {
                if (dfsCiclo(vecino, v, visitado)) {
                    return true;
                }
            }
            else if (!vecino.equals(padre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * vacía el grafo
     */
    @Override
    public void vaciar() {
        adyacencias.clear();
    }
}