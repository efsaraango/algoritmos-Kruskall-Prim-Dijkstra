package algoritmo;

import java.io.*;
import java.util.*;

public class Kruskall {

    // Clase interna para la estructura de datos Union-Find
    public static class UnionFind {
        private int[] parent, rank;

        // Constructor que inicializa las estructuras parent y rank
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Inicialmente, cada nodo es su propio padre
                rank[i] = 0;   // La profundidad del árbol es 0
            }
        }

        // Método find que realiza la compresión de caminos
        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]); // Compresión de caminos
            }
            return parent[i];
        }

        // Método union que une dos conjuntos y mantiene la jerarquía
        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                // Unión por rango
                if (rank[rootI] > rank[rootJ]) {
                    parent[rootJ] = rootI;
                } else if (rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else {
                    parent[rootJ] = rootI;
                    rank[rootI]++;
                }
                return true;
            }
            return false;
        }
    }

    // Método para cargar el grafo desde un archivo de texto
    public static Map<Integer, List<Edge>> cargarGrafo(String nombreArchivo) throws IOException {
        Map<Integer, List<Edge>> grafo = new HashMap<>(); // Estructura para almacenar el grafo
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

    // Método que ejecuta el algoritmo de Kruskal
    public static List<Edge> ejecutarKruskall(Map<Integer, List<Edge>> grafo) {
        List<Edge> mst = new ArrayList<>(); // Lista para almacenar el árbol de expansión mínima
        List<Edge> edges = new ArrayList<>(); // Lista de todas las aristas del grafo
        
        // Convierte las listas de adyacencia en una lista de aristas
        for (List<Edge> adj : grafo.values()) {
            edges.addAll(adj);
        }

        // Ordena las aristas por peso ascendente
        edges.sort(Comparator.comparingInt(e -> e.peso));

        // Inicializa el Union-Find para gestionar los conjuntos
        UnionFind uf = new UnionFind(grafo.size() + 1);

        // Proceso principal del algoritmo de Kruskal
        for (Edge edge : edges) {
            // Si los nodos de la arista no están en el mismo conjunto, únelos y añade la arista al MST
            if (uf.union(edge.origen, edge.destino)) {
                mst.add(edge);
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
