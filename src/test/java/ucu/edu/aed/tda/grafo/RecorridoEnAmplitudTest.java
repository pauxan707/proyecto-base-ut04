package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraphAlgorithms;
 
import java.util.ArrayList;
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;

public class RecorridoEnAmplitudTest {
    private DirectedGraphAlgorithms algoritmos;
 
    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }

    @Test
    void visitaPorNiveles() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D", "E"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("B", "D", new WeightedEdge(1));
        grafo.agregarArista("B", "E", new WeightedEdge(1));
 
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnAmplitud(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);
 
        /**
         * A siempre es el primer elemento
         */
        assertEquals("A", visitados.get(0));
 
        /**
         * D y E deben aparecer después de B y C (que están en nivel 2)
         */
        int idxB = visitados.indexOf("B");
        int idxC = visitados.indexOf("C");
        int idxD = visitados.indexOf("D");
        int idxE = visitados.indexOf("E");
 
        assertTrue(idxB < idxD, "B debe visitarse antes que D");
        assertTrue(idxB < idxE, "B debe visitarse antes que E");
        assertTrue(idxC < idxD || idxC < idxE, "C (nivel 1) debe visitarse antes que los de nivel 2");
        
        /**
         * se visitan los 5 vértices exactamente una vez
         */
        assertEquals(5, visitados.size());
        assertEquals(5, visitados.stream().distinct().count());
    }

    /**
     * Source no existe, entonces consumer nunca se llama
     */
    @Test
    void sourceInexistente_noVisitaNada() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
 
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnAmplitud(grafo, x -> x.equals("C") ? 0 : 1, visitados::add);
 
        assertTrue(visitados.isEmpty());
    }

    /**
     * Source sin aristas salientes solo se visita él mismo
     */
    @Test
    void sourceAislado_soloVisitaSource() {
       DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
    
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnAmplitud(grafo, x -> x.equals("A") ? 0 : 1, visitados::add); 

        assertEquals(List.of("A"), visitados);
    }

    /**
     * ningun vértice debe visitarse más de una vez
     * 
     */
    @Test
    void cadaVerticeVisitadoUnaVez() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "A", new WeightedEdge(1)); 
 
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnAmplitud(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);
 
        assertEquals(3, visitados.size());
        assertEquals(3, visitados.stream().distinct().count());
        assertTrue(visitados.containsAll(List.of("A", "B", "C")));
    }

    /**
     * El orden debe ser exactamente A, B, C, D
     */
    @Test
    void grafoLineal_visitaEnOrdenAmplitud() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(1));
 
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnAmplitud(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);
 
        assertEquals(List.of("A", "B", "C", "D"), visitados);
    }

    /**
     * Vértice inalcanzable desde source no debe ser visitado
     */
    @Test
    void verticeInalcanzable_noEsVisitado() {
      DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
 
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnAmplitud(grafo, x -> x.equals("A") ? 0 : 1, visitados::add);
 
        assertFalse(visitados.contains("C"));
        assertTrue(visitados.containsAll(List.of("A", "B")));  
    }
}
