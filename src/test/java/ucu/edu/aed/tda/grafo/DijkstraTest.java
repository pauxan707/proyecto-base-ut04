package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }

    /**
     * caso básico
     */
    @Test
    void dijkstra_caminoMinimo_basico() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D", "E"));
        grafo.agregarArista("A", "B", new WeightedEdge(2));
        grafo.agregarArista("A", "C", new WeightedEdge(4));
        grafo.agregarArista("B", "D", new WeightedEdge(3));
        grafo.agregarArista("B", "E", new WeightedEdge(1));

        IDijkstraResult<String> resultado = algoritmos.dijkstra(
                x -> x.equals("A") ? 0 : 1, grafo
        );

        assertEquals(2.0, resultado.getCost("B"));
        assertEquals(4.0, resultado.getCost("C"));
        assertEquals(5.0, resultado.getCost("D"));
        assertEquals(3.0, resultado.getCost("E"));

        assertEquals(List.of("A", "B", "D"), resultado.getPath("D"));
        assertEquals(List.of("A", "B", "E"), resultado.getPath("E"));
    }

    /**
     * camino más barato se elige aunque no sea el directo
     */
    @Test
    void dijkstra_elige_caminoIndirectoMasBarato() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(10));
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "B", new WeightedEdge(2));

        IDijkstraResult<String> resultado = algoritmos.dijkstra(
                x -> x.equals("A") ? 0 : 1, grafo
        );

        assertEquals(3.0, resultado.getCost("B"));
        assertEquals(List.of("A", "C", "B"), resultado.getPath("B"));
    }

    /**
     * verifica que se trabaje bien con vertices inalcanzables
     */
    @Test
    void dijkstra_verticeInalcanzable_distanciaInfinita() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(5));
        // aca C no tiene ninguna arista entrante desde A o B

        IDijkstraResult<String> resultado = algoritmos.dijkstra(
                x -> x.equals("A") ? 0 : 1, grafo
        );

        assertEquals(Double.MAX_VALUE, resultado.getCost("C"));
        assertTrue(resultado.getPath("C").isEmpty());
    }
}