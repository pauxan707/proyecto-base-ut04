package ucu.edu.aed.tda.grafo.model.implementaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.IDirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;

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
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo){
        return null;
    }

    /**
     * ejecuta Warshall sobre el grafo pasado, sabiendo que el grafo es weighted
     */
    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo){
        return null;
    }
 
    /**
     * Calcula el centro del grafo, el vertice con menor excentricidad
     * Si hay empate, se devuelve cualquiera de los que comparten el mínimo
     */
    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo) {
        V centro = null;
        double minExcentricidad = Double.MAX_VALUE;
        
        for (V v : grafo.vertices()) {
            double exc = obtenerExcentricidad(grafo, grafo.construirComparable(v));
            if (exc < minExcentricidad) {
                minExcentricidad = exc;
                centro = v;
            }
        }
        return centro;
    }

    /**
     * Calcula la excentrecidad de un vértice
     * La mayor distancia mínima desde ese vértice hacia cualquier otro vértice del grafo
     */
    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo, Comparable<V> vertexCriteria) {
        IFloydWarshallResult<V> fw = floyd(grafo);
        V src = grafo.buscarVertice(vertexCriteria);

        double maxDist = 0;
        for (V w: grafo.vertices()) {
            if (!w.equals(src) && fw.connected(src, w)) {
                double d = fw.getCost(src, w);
                if (d > maxDist) {
                    maxDist = d;
                }
            }
        }
        return maxDist;
    }

    /**
     * Retorna todos los caminos posibles para ir de "source" a "target"
     * Cada Path contiene la lista ordenada de vértices y el costo total
     */
    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(Comparable<V> source, Comparable<V> target, IGraph<V, D> grafo) {
        List<Path<V>> resultado = new ArrayList<>();
        V src = grafo.buscarVertice(source);
        V dst = grafo.buscarVertice(target);
        
        if (src == null || dst == null) {
            return resultado;
        }

        Set<V> visitados = new HashSet<>();
        List<V> caminoActual = new ArrayList<>();
        
        visitados.add(src);
        caminoActual.add(src);

        todosCaminos(src, dst, grafo, visitados, caminoActual, 0.0, resultado);
        
        return resultado;
    }

    /**
     * Auxiliar recursivo de obtenerTodosLosCaminos
     * @param actual nodo que estamos procesando
     * @param destino nodo al que queremos llegar
     * @param grafo grafo sobre el que trabajamos
     * @param visitados conjunto de nodos ya en el camino actual, evitando ciclos
     * @param caminoActual lista de nodos del camino que se está construyendo
     * @param costoActual costo acumulado hasta el nodo actual
     * @param resultado lista donde se acumulan los caminos completos
     */
    private <V, D extends WeightedEdge> void todosCaminos (
        V actual, V destino, IGraph<V, D> grafo,
        Set<V> visitados, List<V> caminoActual,
        double costoActual, List<Path<V>> resultado) {
            if (actual.equals(destino)) {
                resultado.add (new Path<>(new ArrayList<>(caminoActual), costoActual));
                return;
            }
            
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    caminoActual.add(vecino);

                    todosCaminos(vecino, destino, grafo, visitados, caminoActual, costoActual + arista.dato().getWeight(), resultado);

                    caminoActual.remove(caminoActual.size() -1);
                    visitados.remove(vecino);
                }
            }
        }


    /**
     * Aplica un recorrido en profundidad del grafo y pasa los datos al consumer
     */
    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        Set<V> visitados = new HashSet<>();
        V src = grafo.buscarVertice(sourceCriteria);

        if (src != null) {
            visitar(src, grafo, visitados, consumer);
        }
    }

    /**
     * Auxiliar recursivo de recorridoEnProfundidad
     */
    private <V, D> void visitar (V v, IGraph<V, D> grafo, Set <V> visitados, Consumer<V> consumer) {
        visitados.add(v);
        consumer.accept(v);

        for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(v))) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                visitar (vecino, grafo, visitados, consumer);
            }
        }
    }

    /**
     * Aplica un recorrido en amplitud del grafo y pasa los datos al consumer
     */
    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        V src = grafo.buscarVertice(sourceCriteria);
        if (src == null) {
            return;
        }

        Set<V> visitados = new HashSet<>();
        Queue<V> cola = new LinkedList<>();

        visitados.add(src);
        cola.offer(src);

        while (!cola.isEmpty()) {
            V actual = cola.poll();
            consumer.accept(actual);

            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.offer(vecino);
                }
            }
        }
    }

    /**
     * Calcula la clasificación topológica del grafo actual
     */
    @Override
    public <V, D> List<V> calcularClasificacionTopologica(IDirectedIGraph<V, D> grafo) {
        Map<V, Integer> gradoEntrada = new HashMap<>();
        for (V v : grafo.vertices()) {
            gradoEntrada.put(v,0);
        }
        for (Edge <V, D> arista : grafo.aristas()) {
            gradoEntrada.merge(arista.target(), 1, Integer::sum);
        }

        Queue<V> cola = new LinkedList<>();
        for (V v : grafo.vertices()) {
            if (gradoEntrada.get(v) == 0) {
                cola.offer(v);
            }
        }

        List<V> orden = new ArrayList<>();
        while (!cola.isEmpty()) {
            V u = cola.poll();
            orden.add(u);

            for (Edge<V , D> arista : grafo.adyacencias(grafo.construirComparable(u))) {
                V sucesor = arista.target();
                int nuevoGrado = gradoEntrada.get(sucesor) -1;
                gradoEntrada.put(sucesor, nuevoGrado);
                if (nuevoGrado == 0) {
                    cola.offer(sucesor);
                }
            }
        }
        return orden;
    }

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
