package ucu.edu.aed.tda.grafo.model.result;

import java.util.List;

/**
 * Modela el resultado de ejecutar Dijkstra.
 * Ofrece el método obtener costo  y obtener un camino al vértice pasado
 */
public interface IDijkstraResult<V> {
    /**
     * Devuelve el costo para ir a "otherVertex"
     */
    double getCost(V otherVertex);

    /**
     * Retorna el camino para ir a "otherVertex"
     */
    List<V> getPath(V otherVertex); //se me hace raro que devuelva List<V> habiendo una clase Path? igual, no lo toco porque es del proyecto base.
}
