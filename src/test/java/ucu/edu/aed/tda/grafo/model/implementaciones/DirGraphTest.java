package ucu.edu.aed.tda.grafo.model.implementaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class DirGraphTest {

    private DirectedGraph<Integer, String> grafo;
    
    @BeforeEach
    public void setUp() {
        grafo = new DirectedGraph<>();
    }
    
    @Test
    public void testAgregarVertice() {
        assertTrue(grafo.agregarVertice(1));
    }
    
    @Test
    public void testVerticeQueExiste() {
        grafo.agregarVertice(1);
        assertFalse(grafo.agregarVertice(1));
    }
    
    @Test
    public void testAgregarVertices() {
        assertTrue(grafo.agregarVertice(1));
        assertTrue(grafo.agregarVertice(2));
        assertTrue(grafo.agregarVertice(3));
        assertEquals(3, grafo.vertices().size());
    }
    
    @Test
    public void testAgregarArista() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        assertTrue(grafo.agregarArista(1, 2, "peso"));
    }
    
    @Test
    public void testAgregarAristaNoExiste() {
        grafo.agregarVertice(1);
        assertFalse(grafo.agregarArista(1, 2, "peso"));
    }
    
    @Test
    public void testAristaDoble() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        assertTrue(grafo.agregarArista(1, 2, "peso"));
        assertFalse(grafo.agregarArista(1, 2, "otro"));
    }
    
    @Test
    public void testConexoVacio() {
        assertTrue(grafo.esConexo());
    }
    
    @Test
    public void testConexoConUnVertice() {
        grafo.agregarVertice(1);
        assertTrue(grafo.esConexo());
    }
    
    @Test
    public void testConexo() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarArista(1, 2, "a");
        grafo.agregarArista(2, 3, "b");
        grafo.agregarArista(3, 1, "c");
        assertTrue(grafo.esConexo());
    }
    
    @Test
    public void testConexoGrafoInconexo() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarVertice(4);
        grafo.agregarArista(1, 2, "a");
        grafo.agregarArista(3, 4, "b");
        assertFalse(grafo.esConexo());
    }
    
    @Test
    public void testCiclosVacio() {
        assertFalse(grafo.tieneCiclos());
    }
    
    @Test
    public void testCiclosNoCiclos() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarArista(1, 2, "a");
        grafo.agregarArista(2, 3, "b");
        assertFalse(grafo.tieneCiclos());
    }
    
    @Test
    public void testCiclosCon() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarArista(1, 2, "a");
        grafo.agregarArista(2, 3, "b");
        grafo.agregarArista(3, 1, "c");
        assertTrue(grafo.tieneCiclos());
    }
    
    @Test
    public void testVaciar() {
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarArista(1, 2, "peso");
        grafo.vaciar();
        assertEquals(0, grafo.vertices().size());
        assertEquals(0, grafo.aristas().size());
    }
}
