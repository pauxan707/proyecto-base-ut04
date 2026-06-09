package ucu.edu.aed.tda.grafo;

import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.implementaciones.UndirectedGraph;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Esta clase tiene métodos del ejercicio 11 y 12
 */
public class Main {
    
    /**
     * Puntos de articulación en la red de telecomunicaciones de África
     */
    static void ejercicio11() {
        UndirectedGraph<String, WeightedEdge> grafo = new UndirectedGraph<>();
        
        /**
         * Los vértices son las ciudades africanas
         */
        for (String ciudad : new String[] {"Nairobi", "Cairo", "Monrovia","Garoua","Mekele","Praia"}) {
            grafo.agregarVertice(ciudad);
        }

        /**
         * Aristas según la tabla del enunciado
         */
        grafo.agregarArista("Nairobi", "Cairo", new WeightedEdge(1));
        grafo.agregarArista("Nairobi", "Monrovia", new WeightedEdge(1));
        grafo.agregarArista("Nairobi", "Garoua", new WeightedEdge(1));
        grafo.agregarArista("Monrovia", "Garoua", new WeightedEdge(1));
        grafo.agregarArista("Monrovia", "Mekele", new WeightedEdge(1));
        grafo.agregarArista("Garoua", "Mekele", new WeightedEdge(1));
        grafo.agregarArista("Mekele", "Praia", new WeightedEdge(1));

        List<String> puntos = new ArrayList<>();
        for (String ciudad : new ArrayList<>(grafo.vertices())) {
            
            /**
             * Guardar aristas del vértice antes de removerlo
             */
            var aristasGuardadas = new ArrayList<>(grafo.adyacencias(grafo.construirComparable(ciudad)));
            grafo.removerVertice(grafo.construirComparable(ciudad));
            
            if(!grafo.vertices().isEmpty() && !grafo.esConexo()) {
                puntos.add(ciudad);
            }

            /**
             * Restaurar el vértice y sus aristas
             */
            grafo.agregarVertice(ciudad);
            for (var arista : aristasGuardadas) {
                grafo.agregarArista(ciudad, arista.target(), arista.dato());
            }
        }
        System.out.println("Ciudades que son puntos de articulación: ");
        if (puntos.isEmpty()) {
            System.out.println("(ninguna)");
        } else {
            for (String ciudad : puntos) {
                System.out.println("->" + ciudad);
            }
        }
    }

    /**
     * Carga el grafo desde los archivos de actores y películas
     * Formato actores.txt : un actor por línea
     * Formato en_pelicula.txt : actor1,actor2,pelicula (no se usa como peso)
     */
    static UndirectedGraph<String, String> cargarGrafoKevinBacon(String rutaActores, String rutaPeliculas) throws IOException {
        UndirectedGraph<String, String> grafo = new UndirectedGraph<>();

        /**
         * Cargar vértices
         */
        for (String linea : Files.readAllLines(Path.of(rutaActores))) {
            String actor = linea.trim();
            if (!actor.isEmpty()) grafo.agregarVertice(actor);
        }

        /**
         * Cargar aristas
         */
        for (String linea : Files.readAllLines(Path.of(rutaPeliculas))) {
            String[] partes = linea.trim().split(",");
            if (partes.length < 2) continue;
            String actor1 = partes[0].trim();
            String actor2 = partes[1].trim();
            /**
             * pelicula si existe (no requerida)
             */
            String pelicula = partes.length >= 3 ? partes[2].trim() : "?";
            grafo.agregarArista(actor1, actor2, pelicula);
        }
        return grafo;
    }

    /**
     * Calcula el número de Bacon de un actor usando BFS desde "Kevin_Bacon".
     * Retorna -1 si el actor no está conectado a Kevin Bacon en el grafo.
     */
    static int numeroDeBacon(UndirectedGraph<String, String> grafo, String actor) {
        String kevin = "Kevin_Bacon";

        if (grafo.buscarVertice(grafo.construirComparable(kevin)) == null) {
            System.err.println("Kevin_Bacon no está en el grafo.");
            return -1;
        }
        if (grafo.buscarVertice(grafo.construirComparable(actor)) == null) {
            System.err.println(actor + " no está en el grafo.");
            return -1;
        }

        /**
         * BFS desde Kevin_Bacon
         */
        Map<String, Integer> distancia = new HashMap<>();
        Queue<String> cola = new LinkedList<>();
        distancia.put(kevin, 0);
        cola.offer(kevin);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (var arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                String vecino = arista.target();
                if (!distancia.containsKey(vecino)) {
                    distancia.put(vecino, distancia.get(actual) + 1);
                    cola.offer(vecino);
                }
            }
        }

        return distancia.getOrDefault(actor, -1);
    }

    static void ejercicio12() {
        /**
         * Rutas a los archivos
         */
        String rutaActores   = "src/main/java/ucu/resources/actores.txt";
        String rutaPeliculas = "src/main/java/ucu/resources/en_pelicula.txt";

        UndirectedGraph<String, String> grafo;
        try {
            grafo = cargarGrafoKevinBacon(rutaActores, rutaPeliculas);
        } catch (IOException e) {
            System.err.println("No se pudieron cargar los archivos: " + e.getMessage());
            System.err.println("Asegúrate de que actores.txt y en_pelicula.txt están en src/main/java/ucu/resources/");
            return;
        }

        System.out.println("Grafo cargado: "
                + grafo.cantidadDeVertices() + " actores, "
                + grafo.cantidadDeAristas()  + " relaciones");

        String[] actores = {
            "John_Goodman",
            "Tom_Cruise",
            "Jason_Statham",
            "Lukas_Haas",
            "Djimon_Hounsou"
        };

        for (String actor : actores) {
            int bacon = numeroDeBacon(grafo, actor);
            if (bacon == -1) {
                System.out.println(actor + " -> no tiene número de Bacon (no conectado)");
            } else {
                System.out.println(actor + " -> número de Bacon = " + bacon);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ejercicio11();
        ejercicio12();
    }
}