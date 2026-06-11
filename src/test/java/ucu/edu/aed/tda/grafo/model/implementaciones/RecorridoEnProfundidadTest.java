package ucu.edu.aed.tda.grafo.model.implementaciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecorridoEnProfundidadTest {
    
    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }

    @Test
    void grafoLineal_visitaEnOrdenProfundidad() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A","B","C","D"));
        grafo.agregarArista("A","B", new WeightedEdge(1));
        grafo.agregarArista("B","C", new WeightedEdge(1));
        grafo.agregarArista("C","D", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);

        assertEquals(List.of("A","B", "C","D"), visitados);
    }

    /**
     * Source no existe en el grafo, por lo tanto consumer nunca se llama
     */
    @Test
    void sourceInexistente_noVisitaNada() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A","B"));
        grafo.agregarArista("A","B", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, x -> x.equals("Z") ? 0 : 1, visitados::add);

        assertTrue(visitados.isEmpty());
    }

    /**
     * Grafo con vértices sin conexión desde source, solo se visita el source
     */

    @Test
    void sourceAislado_soloVisitaSource() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A","B", "C"));
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);

        assertEquals(List.of("A"), visitados);
    }

    /**
     * Ningún vértice debe ser visitado más de una vez
     */
    @Test
    void cadaVerticeVisitadoUnaVez() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A","B","C"));
        grafo.agregarArista("A","B", new WeightedEdge(1));
        grafo.agregarArista("B","C", new WeightedEdge(1));
        grafo.agregarArista("C","A", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);

        assertEquals(3, visitados.size());
        assertTrue(visitados.contains("A"));
        assertTrue(visitados.contains("B"));
        assertTrue(visitados.contains("C"));
        assertEquals(3, visitados.stream().distinct().count());
    }

    /**
     * Grafo árbol con 2 ramas, desde A se visita exactamente los 5 vértices sin repetir
     */
    @Test
    void visitaTodosLosVerticesAlcanzables() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A","B","C","D", "E"));
        grafo.agregarArista("A","B", new WeightedEdge(1));
        grafo.agregarArista("A","C", new WeightedEdge(1));
        grafo.agregarArista("B","D", new WeightedEdge(1));
        grafo.agregarArista("B","E", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);

        assertEquals("A", visitados.get(0));
        assertEquals(5, visitados.size());
        assertEquals(grafo.vertices().size(), visitados.stream().distinct().count());
        assertTrue(visitados.containsAll(List.of("A","B", "C","D", "E")));
    }

    /**
     * Vertice inalcanzable desde source no debe ser visitado
     */
    @Test
    void verticeInalcanzable_noEsVisitado() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A","B","C"));
        grafo.agregarArista("A","B", new WeightedEdge(1));

        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);

        assertFalse(visitados.contains("C"));
        assertTrue(visitados.containsAll(List.of("A", "B")));
    }
}