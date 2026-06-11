package ucu.edu.aed.tda.grafo.model.implementaciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.Path;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class obtenerTodosLosCaminosTests {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }
 
    /**
     * caso básico donde existen múltiples caminos para llegar al destino
     */
    @Test
    void obtenerTodosLosCaminos_multiplesOpciones_encuentraTodos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "D", new WeightedEdge(1));
        
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(1));

        List<Path<String>> caminos = algoritmos.obtenerTodosLosCaminos("A", "D", grafo);

        assertEquals(2, caminos.size());
    }

    /**
     * verifica que retorne una lista vacía si los vértices están desconectados
     */
    @Test
    void obtenerTodosLosCaminos_sinCaminoPosible_retornaListaVacia() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));

        List<Path<String>> caminos = algoritmos.obtenerTodosLosCaminos("A", "C", grafo);

        assertTrue(caminos.isEmpty());
    }

    /**
     * evita bucles infinitos cuando el grafo contiene ciclos y extrae los caminos válidos
     */
    @Test
    void obtenerTodosLosCaminos_conCicloEnElGrafo_noSeQuedaPegado() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        
        grafo.agregarArista("A", "C", new WeightedEdge(5));
        
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "A", new WeightedEdge(1));

        List<Path<String>> caminos = algoritmos.obtenerTodosLosCaminos("A", "C", grafo);

        assertFalse(caminos.isEmpty());
    }
}
