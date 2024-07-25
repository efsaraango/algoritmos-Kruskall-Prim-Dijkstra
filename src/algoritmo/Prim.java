package algoritmo;

import java.io.*;
import java.util.*;

public class Prim {

    // Método para cargar el grafo desde un archivo de texto
    public static Map<Integer, List<Edge>> cargarGrafo(String nombreArchivo) throws IOException {
        Map<Integer, List<Edge>> grafo = new HashMap<>(); // Estructura de datos para almacenar el grafo
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea;

        // Lee el archivo línea por línea para construir el grafo
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            int origen = Integer.parseInt(partes[0]);
            int destino = Integer.parseInt(partes[1]);
            int peso = Integer.parseInt(partes[2]);

            // Añade las aristas al grafo, asegurando que cada nodo tenga una lista de aristas
            grafo.putIfAbsent(origen, new ArrayList<>());
            grafo.putIfAbsent(destino, new ArrayList<>());
            grafo.get(origen).add(new Edge(origen, destino, peso));
            grafo.get(destino).add(new Edge(destino, origen, peso)); // Para grafo no dirigido
        }
        br.close();
        return grafo; // Retorna el grafo construido
    }

    // Método que ejecuta el algoritmo de Prim
    public static List<Edge> ejecutarPrim(Map<Integer, List<Edge>> grafo, int inicio) {
        List<Edge> mst = new ArrayList<>(); // Lista para almacenar el árbol de expansión mínima
        Set<Integer> visitados = new HashSet<>(); // Conjunto para rastrear nodos visitados
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.peso));
        
        visitados.add(inicio);
        minHeap.addAll(grafo.get(inicio)); // Añade todas las aristas del nodo inicial a la cola de prioridad
        
        // Mientras haya aristas en la cola de prioridad
        while (!minHeap.isEmpty()) {
            Edge edge = minHeap.poll(); // Extrae la arista con el menor peso
            if (visitados.contains(edge.destino)) {
                continue; // Si el nodo destino ya fue visitado, ignora esta arista
            }
            visitados.add(edge.destino); // Marca el nodo destino como visitado
            mst.add(edge); // Añade la arista al MST
            
            // Añade todas las aristas del nodo destino a la cola de prioridad
            for (Edge siguiente : grafo.get(edge.destino)) {
                if (!visitados.contains(siguiente.destino)) {
                    minHeap.offer(siguiente);
                }
            }
        }
        return mst; // Retorna la lista de aristas del MST
    }

    // Método para guardar el MST en un archivo de texto
    public static void guardarResultado(List<Edge> mst, String nombreArchivoSalida) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivoSalida));
        // Escribe cada arista del MST en el archivo
        for (Edge edge : mst) {
            bw.write(edge.origen + "," + edge.destino + "," + edge.peso + "\n");
        }
        bw.close();
    }

    // Método para imprimir el MST en la consola
    public static void imprimirMST(List<Edge> mst) {
        
        // Imprime cada arista del MST
        for (Edge edge : mst) {
            System.out.println("Origen:"+edge.origen + " - " +"Destino:"+ edge.destino + " - "+"Arista:" + edge.peso);
        }
    }
}
