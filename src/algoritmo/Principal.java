package algoritmo;

import java.io.*;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nombreArchivo = "grafo/Grafo30.txt";
        System.out.println("Seleccione el algoritmo que desea ejecutar:");
        System.out.println("1. Prim");
        System.out.println("2. Kruskal");
        System.out.println("3. Dijkstra");
        int opcion = scanner.nextInt();

        try {
            switch (opcion) {
                case 1:
                    System.out.println("Ejecutando Prim...");
                    Map<Integer, List<Edge>> grafoPrim = Prim.cargarGrafo(nombreArchivo);
                    List<Edge> mstPrim = Prim.ejecutarPrim(grafoPrim, 1);
                    Prim.guardarResultado(mstPrim, "resultadografos/MST_Prim.txt");
                    Prim.imprimirMST(mstPrim);
                    break;
                case 2:
                    System.out.println("Ejecutando Kruskal...");
                    Map<Integer, List<Edge>> grafoKruskal = Kruskall.cargarGrafo(nombreArchivo);
                    List<Edge> mstKruskal = Kruskall.ejecutarKruskall(grafoKruskal);
                    Kruskall.guardarResultado(mstKruskal, "resultadografos/MST_Kruskal.txt");
                    Kruskall.imprimirMST(mstKruskal);
                    break;
                case 3:
                    System.out.println("Ejecutando Dijkstra...");
                    Map<Integer, List<Edge>> grafoDijkstra = Dijkstra.cargarGrafo(nombreArchivo);
                    Map<Integer, Integer> distancias = Dijkstra.ejecutarDijkstra(grafoDijkstra, 1);
                    Dijkstra.guardarResultado(distancias, "resultadografos/Dijkstra.txt");
                    Dijkstra.imprimirResultados(distancias);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
