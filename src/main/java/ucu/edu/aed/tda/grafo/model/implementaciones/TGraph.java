package ucu.edu.aed.tda.grafo.model.implementaciones;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.Iterator;

public abstract class TGraph<V, D> implements IGraph<V, D>{

    protected HashMap<V, List<Edge<V, D>>> adyacencias;

    public TGraph() {
        this.adyacencias = new HashMap<>();
    }

    
    @Override
    public boolean agregarVertice(V vertex) {
        if (vertex == null || adyacencias.containsKey(vertex)) {
            return false;
        }

        adyacencias.put(vertex, new ArrayList<>());
        return true;
    }

    public boolean esVacio() {
        return adyacencias.isEmpty();
    }

    @Override
    public void vaciar() {
        adyacencias.clear();
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(adyacencias.keySet());
    }

    @Override
    public List<Edge<V,D>> adyacencias(Comparable<V> verticeCriteria) {

        for (V vertice : adyacencias.keySet()) {
            if (verticeCriteria.compareTo(vertice) == 0) {
                return Collections.unmodifiableList(adyacencias.get(vertice));
            }
        }

        return null;
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

    @Override
    public abstract boolean agregarArista(V source, V target, D dato);

    @Override
    public abstract boolean eliminarArista(Comparable<V> source, Comparable<V> target);

    @Override
    public boolean removerVertice(Comparable<V> criteria) {

        V vertice = buscarVertice(criteria);

        if (vertice == null) {
            return false;
        }

        adyacencias.remove(vertice);

        for (List<Edge<V, D>> aristas : adyacencias.values()) {
            Iterator<Edge<V, D>> i = aristas.iterator();
            while (i.hasNext()) {
                if (criteria.compareTo(i.next().target()) == 0) {
                    i.remove();
                }
            }
        }

        return true;
    }

    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        return obtenerArista(sourceCriteria, targetCriteria) != null;
    }

    @Override
    public abstract Set<Edge<V, D>> aristas();

    @Override
    public abstract Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria);

    @Override
    public abstract boolean esConexo();

    @Override
    public abstract boolean tieneCiclos();

}
