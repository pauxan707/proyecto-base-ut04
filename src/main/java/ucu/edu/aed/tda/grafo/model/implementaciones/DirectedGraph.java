package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

public class DirectedGraph<V, D> extends TGraph<V, D> implements IDirectedIGraph<V, D> {

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

    @Override
    public Set<Edge<V, D>> aristas() {

        Set<Edge<V, D>> setAristas = new HashSet<>();

        for (List<Edge<V,D>> aristas : adyacencias.values()) {
            setAristas.addAll(aristas);
        }

        return Collections.unmodifiableSet(setAristas);
    }

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

    @Override
    public Set<V> predecessors(Comparable<V> criteria) {

        V vertice = buscarVertice(criteria);

        Set<V> predecesores = new HashSet<>();

        if (vertice != null) {
            for (V v : adyacencias.keySet()) { 

                if (obtenerArista(construirComparable(v), criteria) != null){ 
                    predecesores.add(v);
                }

            }
        }

        return predecesores;
    }

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
}