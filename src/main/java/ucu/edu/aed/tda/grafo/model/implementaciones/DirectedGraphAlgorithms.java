package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.ArrayList;
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
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo) {
        Map<V, Double> distancia = new HashMap<>();
        Map<V, List<V>> caminos = new HashMap<>();
        Set<V> visitados = new HashSet<>();

        V sourceVertex = grafo.buscarVertice(source);

        //inicializamos distancias en "infinito" (MAX_VALUE)
        for (V v : grafo.vertices()) {
            distancia.put(v, Double.MAX_VALUE);
            caminos.put(v, new ArrayList<>());
        }

        //origen distancia 0
        distancia.put(sourceVertex, 0.0);
        caminos.get(sourceVertex).add(sourceVertex);

        //while hayan vertices sin visitar
        while (visitados.size() < grafo.cantidadDeVertices()) {

            //se busca el vertice sin visitar a menor distancia
            V actual = null;
            double menorDist = Double.MAX_VALUE;
            for (V v : grafo.vertices()) {
                if (!visitados.contains(v) && distancia.get(v) < menorDist) {
                    menorDist = distancia.get(v);
                    actual = v;
                }
            }

            //Si queda alguno que no se pueda alcanzar, break
            if (actual == null) break;

            visitados.add(actual);

            //se comparan nuevas aristas para ver si tienen mejores caminos
            for (var arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                V vecino = arista.target();
                double nuevaDist = distancia.get(actual) + arista.dato().getWeight();

                if (nuevaDist < distancia.get(vecino)) {
                    distancia.put(vecino, nuevaDist);

                   
                    List<V> nuevoCamino = new ArrayList<>(caminos.get(actual));
                    nuevoCamino.add(vecino);
                    caminos.put(vecino, nuevoCamino);
                }
            }
        }

        return new DijkstraResult<>(sourceVertex, distancia, caminos);
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
