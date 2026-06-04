package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraphAlgorithms;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class calcularClasificacionTopologicaTests {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }
 
    //caso básico donde el grafo se puede ordenar perfectamente
    @Test
    void calcularClasificacionTopologica_casoEstandar_ordenaCorrectamente() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));

        List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);

        assertEquals(List.of("A", "B", "C"), orden);
    }

    //cuando el grafo tiene un ciclo no se puede hacer la clasificación completa (quedan nodos sin procesar)
    @Test
    void calcularClasificacionTopologica_conCiclo_retornaListaIncompleta() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "A", new WeightedEdge(1));

        List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);

        assertTrue(orden.isEmpty());
    }

    //un grafo con múltiples componentes desconectados pero sin ciclos genera un orden válido
    @Test
    void calcularClasificacionTopologica_grafosDesconectados_procesaTodosLosNodos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertices(List.of("A", "B", "C", "D"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(1));

        List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);

        assertEquals(4, orden.size());
        
        assertTrue(orden.indexOf("A") < orden.indexOf("B"));
        assertTrue(orden.indexOf("C") < orden.indexOf("D"));
    }

    //un grafo sin aristas
    @Test
    void calcularClasificacionTopologica_sinAristas_retornaTodosLosVertices() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));

        List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);

        assertEquals(3, orden.size());
        assertTrue(orden.containsAll(List.of("A", "B", "C")));
    }
}
