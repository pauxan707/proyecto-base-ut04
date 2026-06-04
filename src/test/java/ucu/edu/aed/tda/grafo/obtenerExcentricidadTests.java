package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraph;
import ucu.edu.aed.tda.grafo.model.implementaciones.DirectedGraphAlgorithms;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class obtenerExcentricidadTests {

    private DirectedGraphAlgorithms algoritmos;

    @BeforeEach
    void setUp() {
        algoritmos = new DirectedGraphAlgorithms();
    }
 
    //caso básico de excentricidad en un grafo lineal
    @Test
    void obtenerExcentricidad_casoLineal_calculaMaximoCaminoMinimo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(2));
        grafo.agregarArista("B", "C", new WeightedEdge(3));

        double excentricidadA = algoritmos.obtenerExcentricidad(grafo, "A");

        assertEquals(5.0, excentricidadA);
    }

    //la excentricidad considera el camino indirecto más barato en lugar del directo costoso
    @Test
    void obtenerExcentricidad_consideraCaminoIndirectoMasCorto() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(10)); 
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "B", new WeightedEdge(2));  

        double excentricidadA = algoritmos.obtenerExcentricidad(grafo, "A");

        assertEquals(3.0, excentricidadA);
    }

    //verifica el resultado cuando el vértice evaluado no puede llegar a todo el grafo
    @Test
    void obtenerExcentricidad_verticeInalcanzable_retornaInfinito() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "B", new WeightedEdge(4));

        double excentricidadA = algoritmos.obtenerExcentricidad(grafo, "A");

        assertEquals(Double.MAX_VALUE, excentricidadA);
    }
}
