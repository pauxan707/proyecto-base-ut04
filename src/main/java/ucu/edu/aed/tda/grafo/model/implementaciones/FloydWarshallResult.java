package ucu.edu.aed.tda.grafo.model.implementaciones;

import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

import java.util.List;
import java.util.Map;

public class FloydWarshallResult<V> implements IFloydWarshallResult<V> {

    private final Map<V, Map<V, Double>> costos;
    private final Map<V, Map<V, List<V>>> caminos;

    public FloydWarshallResult(Map<V, Map<V, Double>> costos, Map<V, Map<V, List<V>>> caminos) {
        this.costos = costos;
        this.caminos = caminos;
    }

    @Override
    public double getCost(V source, V target) {
        return costos.getOrDefault(source, Map.of()).getOrDefault(target, Double.MAX_VALUE);
    }

    @Override
    public List<V> getPath(V source, V target) {
        return caminos.getOrDefault(source, Map.of()).getOrDefault(target, List.of());
    }

    @Override
    public boolean connected(V source, V target) {
        return getCost(source, target) < Double.MAX_VALUE;
    }
}