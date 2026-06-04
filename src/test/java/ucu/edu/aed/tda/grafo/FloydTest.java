package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FloydTest {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }

    //caso básico de matriz de caminos mínimos entre todos los pares
    @Test
    void floyd_caminoMinimo_basico() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(10));
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "B", new WeightedEdge(2));

        IFloydWarshallResult<String> resultado = algoritmos.floyd(grafo);

        // el camino más corto de A a B debe ser a través de C (costo 3.0)
        assertEquals(3.0, resultado.getCost("A", "B"));
        assertEquals(List.of("A", "C", "B"), resultado.getPath("A", "B"));
        
        // directo de C a B
        assertEquals(2.0, resultado.getCost("C", "B"));
    }

    //verifica que se trabaje bien con vertices inalcanzables entre sí
    @Test
    void floyd_verticeInalcanzable_distanciaInfinita() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(5));
        // C queda completamente aislado

        IFloydWarshallResult<String> resultado = algoritmos.floyd(grafo);

        assertEquals(Double.MAX_VALUE, resultado.getCost("A", "C"));
        assertEquals(Double.MAX_VALUE, resultado.getCost("B", "C"));
        assertTrue(resultado.getPath("A", "C").isEmpty());
    }
}
