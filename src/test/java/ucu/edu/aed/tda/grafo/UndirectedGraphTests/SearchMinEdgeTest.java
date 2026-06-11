package ucu.edu.aed.tda.grafo.UndirectedGraphTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraphAlgorithms;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SearchMinEdgeTest {

    private UndirectedGraphAlgorithms algoritmos;
    private UndirectedGraph<String, WeightedEdge> grafo;

    @BeforeEach
    void setUp() {
        algoritmos = new UndirectedGraphAlgorithms();
        grafo = new UndirectedGraph<>();
    }

    /**
     * Caso básico: busca la arista mínima entre dos conjuntos U y V
     * U contiene "A", V contiene "B" y "C"
     * La arista mínima debe ser A-B con peso 1
     */
    @Test
    void searchMinEdge_casoBasico_encuentraMinima() {
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(3));

        Collection<String> U = new HashSet<>();
        U.add("A");
        
        Collection<String> V = new HashSet<>();
        V.add("B");
        V.add("C");

        Edge<String, WeightedEdge> minEdge = algoritmos.searchMinEdge(grafo, U, V);

        /**
         * La arista mínima debe ser A-B con peso 1
         */
        assertNotNull(minEdge);
        assertEquals(1.0, minEdge.dato().getWeight());
        assertTrue(
            (minEdge.source().equals("A") && minEdge.target().equals("B")) ||
            (minEdge.source().equals("B") && minEdge.target().equals("A"))
        );
    }

    /**
     * Verifica que selecciona la arista más barata cuando hay múltiples opciones
     * U contiene "A" y "B", V contiene "C" y "D"
     */
    @Test
    void searchMinEdge_multipleCandidatos_seleccionaMasBarata() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "C", new WeightedEdge(5));
        grafo.agregarArista("A", "D", new WeightedEdge(7));
        grafo.agregarArista("B", "C", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(4));

        Collection<String> U = new HashSet<>();
        U.add("A");
        U.add("B");
        
        Collection<String> V = new HashSet<>();
        V.add("C");
        V.add("D");

        Edge<String, WeightedEdge> minEdge = algoritmos.searchMinEdge(grafo, U, V);

        /**
         * La arista mínima debe ser B-C con peso 2
         */
        assertNotNull(minEdge);
        assertEquals(2.0, minEdge.dato().getWeight());
        assertTrue(
            (minEdge.source().equals("B") && minEdge.target().equals("C")) ||
            (minEdge.source().equals("C") && minEdge.target().equals("B"))
        );
    }

    /**
     * No hay aristas que conecten U y V: retorna null
     */
    @Test
    void searchMinEdge_sinAristas_retornaNull() {
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        // Solo hay arista dentro de U: A-B
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        // Solo hay arista dentro de V: C-D
        grafo.agregarArista("C", "D", new WeightedEdge(1));
        // Sin aristas entre U y V

        Collection<String> U = new HashSet<>();
        U.add("A");
        U.add("B");
        
        Collection<String> V = new HashSet<>();
        V.add("C");
        V.add("D");

        Edge<String, WeightedEdge> minEdge = algoritmos.searchMinEdge(grafo, U, V);

        assertNull(minEdge);
    }

    /**
     * Conjunto U vacío: retorna null
     */
    @Test
    void searchMinEdge_UVacio_retornaNull() {
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));

        Collection<String> U = new HashSet<>();
        // U está vacío
        
        Collection<String> V = new HashSet<>();
        V.add("B");
        V.add("C");

        Edge<String, WeightedEdge> minEdge = algoritmos.searchMinEdge(grafo, U, V);

        assertNull(minEdge);
    }

    /**
     * Un solo candidato: retorna esa arista
     */
    @Test
    void searchMinEdge_unCandidate_retornaEse() {
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(5));
        grafo.agregarArista("A", "C", new WeightedEdge(10)); // Diferente

        Collection<String> U = new HashSet<>();
        U.add("A");
        
        Collection<String> V = new HashSet<>();
        V.add("B");

        Edge<String, WeightedEdge> minEdge = algoritmos.searchMinEdge(grafo, U, V);

        assertNotNull(minEdge);
        assertEquals(5.0, minEdge.dato().getWeight());
    }

}
