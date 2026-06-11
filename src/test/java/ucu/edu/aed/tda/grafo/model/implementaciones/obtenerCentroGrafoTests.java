package ucu.edu.aed.tda.grafo.model.implementaciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class obtenerCentroGrafoTests {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }

    @Test
    void obtenerCentroGrafo_casoEstandar() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "A", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "B", new WeightedEdge(1));

        String centro = algoritmos.obtenerCentroGrafo(grafo);

        assertEquals("B", centro);
    }

    /**
     * verifica el comportamiento de obtenerCentroGrafo cuando hay vértices inalcanzables
     */
    @Test
    void obtenerCentroGrafo_grafoDesconectado_retornaVerticeSegunAlgoritmo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "A", new WeightedEdge(1));

        String centro = algoritmos.obtenerCentroGrafo(grafo);

        assertEquals("C", centro); 
    }
}