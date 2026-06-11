package ucu.edu.aed.tda.grafo.UndirectedGraphTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraphAlgorithms;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KruskalTest {

    private UndirectedGraphAlgorithms algoritmos;
    private UndirectedGraph<String, WeightedEdge> grafo;

    @BeforeEach
    void setUp() {
        algoritmos = new UndirectedGraphAlgorithms();
        grafo = new UndirectedGraph<>();
    }

    /**
     * Caso básico: árbol de expansión mínima con Kruskal
     * Verifica que el resultado tenga N-1 aristas para N vértices
     */
    @Test
    void kruskal_casoBasico_arbolMinimo() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(4));
        grafo.agregarArista("B", "C", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(5));
        grafo.agregarArista("C", "D", new WeightedEdge(3));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.kruskal(grafo);

        /**
         * El árbol debe tener todos los 4 vértices
         */
        assertEquals(4, mst.cantidadDeVertices());

        /**
         * Un árbol de N vértices tiene exactamente N-1 aristas
         */
        assertEquals(3, mst.cantidadDeAristas());
    }

    /**
     * Verifica que Kruskal selecciona las aristas más baratas
     * Ordena por peso: A-B(1), B-C(2), C-D(3) = total 6
     */
    @Test
    void kruskal_pesoMinimo_seleccionaAristasBaratas() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(4));
        grafo.agregarArista("B", "C", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(5));
        grafo.agregarArista("C", "D", new WeightedEdge(3));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.kruskal(grafo);

        /**
         * El MST debe contener las aristas de menor peso
         */
        assertTrue(mst.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertTrue(mst.existeArista(grafo.construirComparable("B"), grafo.construirComparable("C")));
        assertTrue(mst.existeArista(grafo.construirComparable("C"), grafo.construirComparable("D")));

        /**
         * No debe contener aristas más caras
         */
        assertFalse(mst.existeArista(grafo.construirComparable("A"), grafo.construirComparable("C")));
        assertFalse(mst.existeArista(grafo.construirComparable("B"), grafo.construirComparable("D")));
    }

    /**
     * Grafo con un solo vértice: MST trivial
     */
    @Test
    void kruskal_unSoloVertice() {
        grafo.agregarVertice("A");

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.kruskal(grafo);

        assertEquals(1, mst.cantidadDeVertices());
        assertEquals(0, mst.cantidadDeAristas());
    }

    /**
     * Dos vértices con una arista
     */
    @Test
    void kruskal_dosVertices() {
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(5));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.kruskal(grafo);

        assertEquals(2, mst.cantidadDeVertices());
        assertEquals(1, mst.cantidadDeAristas());
        assertTrue(mst.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
    }

}
