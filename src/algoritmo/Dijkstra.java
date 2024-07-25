package algoritmo;

import java.io.*;
import java.util.*;
import algoritmo.Edge; // Importa la clase Edge del paquete algoritmo

public class Dijkstra {

    // Método para cargar el grafo desde un archivo de texto
    public static Map<Integer, List<Edge>> cargarGrafo(String nombreArchivo) throws IOException {
        Map<Integer, List<Edge>> grafo = new HashMap<>(); // Inicializa la estructura del grafo
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea;

        // Lee cada línea del archivo y construye el grafo
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            int origen = Integer.parseInt(partes[0]);
            int destino = Integer.parseInt(partes[1]);
            int peso = Integer.parseInt(partes[2]);

            // Añade los nodos y aristas al grafo
            grafo.putIfAbsent(origen, new ArrayList<>());
            grafo.get(origen).add(new Edge(origen, destino, peso));
        }
        br.close();
        return grafo; // Retorna el grafo construido
    }

    // Método para ejecutar el algoritmo de Dijkstra
    public static Map<Integer, Integer> ejecutarDijkstra(Map<Integer, List<Edge>> grafo, int inicio) {
        Map<Integer, Integer> distancias = new HashMap<>(); // Almacena las distancias más cortas
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        minHeap.offer(new int[]{inicio, 0}); // Inicializa el heap con el nodo de inicio
        distancias.put(inicio, 0);

        // Mientras haya nodos por procesar
        while (!minHeap.isEmpty()) {
            int[] actual = minHeap.poll(); // Obtiene el nodo con la menor distancia
            int nodo = actual[0];
            int distanciaActual = actual[1];

            // Si la distancia actual es mayor que la registrada, continúa
            if (distanciaActual > distancias.getOrDefault(nodo, Integer.MAX_VALUE)) {
                continue;
            }

            // Recorre las aristas adyacentes
            for (Edge arista : grafo.getOrDefault(nodo, new ArrayList<>())) {
                int nuevaDistancia = distanciaActual + arista.peso;
                
                // Si se encuentra una distancia más corta, se actualiza
                if (nuevaDistancia < distancias.getOrDefault(arista.destino, Integer.MAX_VALUE)) {
                    distancias.put(arista.destino, nuevaDistancia);
                    minHeap.offer(new int[]{arista.destino, nuevaDistancia});
                }
            }
        }
        return distancias; // Retorna el mapa de distancias más cortas
    }

    // Método para guardar los resultados en un archivo de texto
    public static void guardarResultado(Map<Integer, Integer> distancias, String nombreArchivoSalida) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivoSalida));
        // Escribe cada nodo y su distancia en el archivo
        for (Map.Entry<Integer, Integer> entry : distancias.entrySet()) {
            bw.write(entry.getKey() + "," + entry.getValue() + "\n");
        }
        bw.close();
    }

    // Método para imprimir los resultados en la consola
    public static void imprimirResultados(Map<Integer, Integer> distancias) {
       
        // Imprime cada nodo y su distancia más corta
        for (Map.Entry<Integer, Integer> entry : distancias.entrySet()) {
            System.out.println("Nodo:"+entry.getKey() + " - " +"Distacia:"+ entry.getValue());
        }
    }
}
