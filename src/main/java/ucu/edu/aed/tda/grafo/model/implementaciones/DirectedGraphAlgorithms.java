package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.IDirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

public class DirectedGraphAlgorithms implements IDirectedGraphAlgorithms {

    /**
     * ejecuta el algoritmos Dijkstra sobre el grafo pasado y utilizando source como vértice de origen
     */
    @Override
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo){
        Map<V, Double> distancia = new HashMap<>();
        Map<V, List<V>> caminos = new HashMap<>();
        Set<V> visitados = new HashSet<>();

        V sourceVertex = grafo.buscarVertice(source);
    }

    /**
     * ejecuta Floyd sobre el grafo pasado, sabiendo que el grafo es weighted
     */
    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo){}

    /**
     * ejecuta Warshall sobre el grafo pasado, sabiendo que el grafo es weighted
     */
    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo){}

    /**
     * Calcula el centro del grafo
     */
    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo){}

    /**
     * Calcula la excentrecidad de un vértice
     */
    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo, Comparable<V> vertexCriteria){}

    /**
     * Retorna todos los caminos posibles para ir de "source" a "target"
     */
    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(Comparable<V> source, Comparable<V> target, IGraph<V, D> grafo){}

    /**
     * Aplica un recorrido en profundidad del grafo y pasa los datos al consumer
     */
    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer){}

    /**
     * Aplica un recorrido en amplitud del grafo y pasa los datos al consumer
     */
    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer){}

    /**
     * Calcula la clasificación topológica del grafo actual
     */
    @Override
    public <V, D> List<V> calcularClasificacionTopologica(IDirectedIGraph<V, D> grafo){}

    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Consumer<V> consumer) {
        for (V vertex : grafo.vertices()) {
            recorridoEnProfundidad(grafo, grafo.construirComparable(vertex), consumer);
        }
    }

    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Consumer<V> consumer) {
        for (V v : grafo.vertices()) {
            recorridoEnAmplitud(grafo, grafo.construirComparable(v), consumer);
        }
    }
}
