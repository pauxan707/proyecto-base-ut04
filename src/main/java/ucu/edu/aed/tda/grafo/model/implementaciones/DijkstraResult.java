package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.List;
import java.util.Map;

import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;

public class DijkstraResult<V> implements IDijkstraResult<V>{
    private V source;
    private Map<V, Double> distancia;
    private Map<V, List<V>> caminos;
    
    public DijkstraResult(V source, Map<V, Double> distancia, Map<V, List<V>>  caminos) {
        this.source = source;
        this.distancia = distancia;
        this.caminos =  caminos; 
    }

    /**
     * Retorna el vertice origen desde el cual se ejecuto dijkstra
     */
    public V getSource() { 
        return source;
    }

    /**
     * Devuelve el costo para ir a "otherVertex"
     */
    @Override
    public double getCost(V otherVertex){
        return distancia.get(otherVertex);
    }

    /**
     * Retorna el camino para ir a "otherVertex"
     */
    @Override
    public List<V> getPath(V otherVertex){
       return caminos.get(otherVertex);}
}

