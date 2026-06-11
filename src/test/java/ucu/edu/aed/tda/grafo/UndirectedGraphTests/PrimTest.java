package ucu.edu.aed.tda.grafo.UndirectedGraphTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraphAlgorithms;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PrimTest {

    private UndirectedGraphAlgorithms algoritmos;
    private UndirectedGraph<String, WeightedEdge> grafo;

    @BeforeEach
    void setUp() {
        algoritmos = new UndirectedGraphAlgorithms();
        grafo = new UndirectedGraph<>();
    }

    /**
     * Caso básico: árbol de expansión mínima sobre un grafo simple
     * Verifica que el resultado sea válido
     */
    @Test
    void prim_casoBasico_arbolMinimo() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(4));
        grafo.agregarArista("B", "C", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(5));
        grafo.agregarArista("C", "D", new WeightedEdge(3));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.prim(grafo, grafo.construirComparable("A"));

        /**
         * El árbol debe tener 4 vértices
         */
        assertEquals(4, mst.cantidadDeVertices());

        /**
         * Un árbol de N vértices tiene N-1 aristas
         */
        assertEquals(3, mst.cantidadDeAristas());
    }

    /**
     * Verifica que todas las aristas del MST son las de menor peso
     * A-B(1) + B-C(2) + C-D(3) = 6 es el mínimo
     */
    @Test
    void prim_pesoMinimo_seleccionaAristasBaratas() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(4));
        grafo.agregarArista("B", "C", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(5));
        grafo.agregarArista("C", "D", new WeightedEdge(3));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.prim(grafo, grafo.construirComparable("A"));

        /**
         * El MST debe contener las aristas de menor peso:
         * A-B(1), B-C(2), C-D(3)
         */
        assertTrue(mst.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertTrue(mst.existeArista(grafo.construirComparable("B"), grafo.construirComparable("C")));
        assertTrue(mst.existeArista(grafo.construirComparable("C"), grafo.construirComparable("D")));

        /**
         * No debe contener la arista cara A-C(4) ni B-D(5)
         */
        assertFalse(mst.existeArista(grafo.construirComparable("A"), grafo.construirComparable("C")));
        assertFalse(mst.existeArista(grafo.construirComparable("B"), grafo.construirComparable("D")));
    }

    /**
     * Grafo con un solo vértice: el MST es trivial
     */
    @Test
    void prim_unSoloVertice() {
        grafo.agregarVertice("A");

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.prim(grafo, grafo.construirComparable("A"));

        assertEquals(1, mst.cantidadDeVertices());
        assertEquals(0, mst.cantidadDeAristas());
    }

    /**
     * Grafo con dos vértices y una sola arista
     */
    @Test
    void prim_dosVertices() {
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(5));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.prim(grafo, grafo.construirComparable("A"));

        assertEquals(2, mst.cantidadDeVertices());
        assertEquals(1, mst.cantidadDeAristas());
        assertTrue(mst.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
    }

    /**
     * Source inválido retorna null
     */
    @Test
    void prim_sourceInvalido_retornaNull() {
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(2));

        IUndirectedGraph<String, WeightedEdge> mst = algoritmos.prim(grafo, grafo.construirComparable("Z"));

        assertNull(mst);
    }

}
