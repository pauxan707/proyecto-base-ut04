package ucu.edu.aed.tda.grafo.UndirectedGraphTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraphAlgorithms;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BeaTest {
    
    private UndirectedGraphAlgorithms algoritmos;
    private UndirectedGraph<String, WeightedEdge> grafo;

    @BeforeEach
    void setUp() {
        algoritmos = new UndirectedGraphAlgorithms();
        grafo = new UndirectedGraph<>();
    }

    /**
     * Caso mas basico: recorrido en amplitud sobre un grafo simple
     * Verifica que se visiten todos los vértices exactamente UNA vez
     */
    @Test
    void bea_casoBasico_visitaTodos() {
        grafo.agregarVertices(List.of("A", "B", "C", "D", "E"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("B", "D", new WeightedEdge(1));
        grafo.agregarArista("B", "E", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.bea(grafo, visitados::add);

        /**
         * Se corrobora que se visitan los 5 vértices exactamente una vez
         */
        assertEquals(5, visitados.size());
        assertEquals(5, visitados.stream().distinct().count());
    }

    /**
     * Verifica que cada vértice se visite exactamente una vez
     * incluso en un grafo con ciclos
     */
    @Test
    void bea_conCiclos_cadaVerticeVisitadoUnaVez() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(1));
        grafo.agregarArista("D", "A", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.bea(grafo, visitados::add);

        assertEquals(4, visitados.size());
        assertEquals(4, visitados.stream().distinct().count());
    }

    /**
     * Grafo desconectado: verifica que se visiten todos los vértices
     * de múltiples componentes conectadas exactamente una vez
     */
    @Test
    void bea_grafoDesco_visitaTodosLosComponentes() {
        grafo.agregarVertices(List.of("A", "B", "C", "D", "E", "F"));
        // Componente 1: A-B-C
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        // Componente 2: D-E-F
        grafo.agregarArista("D", "E", new WeightedEdge(1));
        grafo.agregarArista("E", "F", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.bea(grafo, visitados::add);

        /**
         * Se visitan todos los 6 vértices exactamente una vez
         */
        assertEquals(6, visitados.size());
        assertTrue(visitados.contains("A"));
        assertTrue(visitados.contains("D"));
    }

    /**
     * Vértice aislado: un vértice sin aristas es visitado exactamente una vez
     */
    @Test
    void bea_verticeAislado_seVisita() {
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarVertice("C");

        List<String> visitados = new ArrayList<>();
        algoritmos.bea(grafo, visitados::add);

        /**
         * Los 3 vértices son visitados exactamente una vez
         */
        assertEquals(3, visitados.size());
        assertTrue(visitados.contains("C"));
    }

}
