package ucu.edu.aed.tda.grafo.model.implementaciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarshallTests {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }

    /**
     * verifica la clausura transitiva básica para conectividad
     */
    @Test
    void warshall_conectividad_basica() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));

        IFloydWarshallResult<String> resultado = algoritmos.warshall(grafo);

        // A llega a C indirectamente a través de B, pero C no puede volver a A
        assertTrue(resultado.connected("A", "C")); 
        assertFalse(resultado.connected("C", "A"));
    }
}
