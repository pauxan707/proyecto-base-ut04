package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.List;

import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;

public class DijkstraResult<V> implements IDijkstraResult<V> {
    /**
     * Devuelve el costo para ir a "otherVertex"
     */
    @Override
    public double getCost(V otherVertex) {
        // Implementación mínima: retornar 0.0 por defecto
        return 0.0;
    }

    /**
     * Retorna el camino para ir a "otherVertex"
     */
    @Override
    public List<V> getPath(V otherVertex) {
        // Implementación mínima: retornar null por defecto
        return null;
    }
}
}
