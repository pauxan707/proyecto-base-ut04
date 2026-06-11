package ucu.edu.aed.tda.grafo.UndirectedGraphTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraph;


public class UnDirGraphTest {
    
    private UndirectedGraph<Integer, String> grafo;
    
    @BeforeEach
    public void setUp() {
        grafo = new UndirectedGraph<>();
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

    @Test
    public void removerVerticeTest(){
        grafo.agregarVertice(1);
        grafo.removerVertice(1);
        assertEquals(0, grafo.vertices().size());
    }

    @Test
    public void removerVerticeInexistente(){
        grafo.removerVertice(1);
        assertEquals(0, grafo.vertices().size());
    }

    @Test
    public void eliminarArista(){
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarArista(1, 2, "peso");
        assertTrue(grafo.eliminarArista(1, 2));
        assertFalse(grafo.existeArista(1, 2));
    }
    
    @Test
    public void eliminarAristaInexstente(){
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        assertFalse(grafo.eliminarArista(1, 2));
    }

    @Test
    public void obtenerAristaExistente(){
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarArista(1, 2, "hola");
        assertTrue(grafo.obtenerArista(1, 2) != null);
    }

    @Test
    public void obtenerAristaInexistente(){
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        assertFalse(grafo.obtenerArista(1, 2) != null);
    }

    @Test
    public void adyacenciasConAristas(){
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarArista(1, 2, "a");
        grafo.agregarArista(1, 3, "b");
        assertEquals(2, grafo.adyacencias(1).size());
    }

    @Test
    public void adyacenciasSinAristas(){
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        assertEquals(0, grafo.adyacencias(1).size());
    }

    @Test
    public void adyacenciasVerticeInexistente(){
        grafo.agregarVertice(1);
        assertEquals(0, grafo.adyacencias(2).size());
    }

}
