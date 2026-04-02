import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static class GraphData {
        int vertices;
        List<KruskalMST.Edge> edges;
        List<PrimMST.Edge>[] adjacency;
    }

// Menú que permite cargar grafos y ejecutar los algoritmos.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GraphData graph = null;
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    graph = cargarGrafo(sc);
                    System.out.println("Grafo cargado correctamente.");
                    break;
                case 2:
                    if (hayGrafo(graph)) {
                        ejecutarPrim(sc, graph);
                    }
                    break;
                case 3:
                    if (hayGrafo(graph)) {
                        ejecutarKruskal(graph);
                    }
                    break;
                case 4:
                    if (hayGrafo(graph)) {
                        ejecutarPrim(sc, graph);
                        ejecutarKruskal(graph);
                    }
                    break;
                case 5:
                    salir = true;
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=============== MENÚ PRINCIPAL ===============");
        System.out.println("1. Ingresar nuevo conjunto de datos");
        System.out.println("2. Ejecutar Prim");
        System.out.println("3. Ejecutar Kruskal");
        System.out.println("4. Ejecutar Prim y Kruskal");
        System.out.println("5. Salir");
        System.out.println("==============================================");
    }

    private static boolean hayGrafo(GraphData graph) {
        if (graph == null) {
            System.out.println("Primero debe ingresar un conjunto de datos.");
            return false;
        }
        return true;
    }

    private static GraphData cargarGrafo(Scanner sc) {
        GraphData graph = new GraphData();
        graph.vertices = leerEnteroMayorOIgual(sc, "Ingrese la cantidad de vértices: ", 1);
        int m = leerEnteroMayorOIgual(sc, "Ingrese la cantidad de aristas: ", 0);

        @SuppressWarnings("unchecked")
        List<PrimMST.Edge>[] adj = new ArrayList[graph.vertices];
        for (int i = 0; i < graph.vertices; i++) {
            adj[i] = new ArrayList<>();
        }

        List<KruskalMST.Edge> edges = new ArrayList<>();

        System.out.println("Ingrese cada arista con el formato: origen destino peso");
        System.out.println("Los vértices se numeran desde 0 hasta " + (graph.vertices - 1) + ".");

        for (int i = 0; i < m; i++) {
            System.out.println("Arista " + (i + 1) + ":");
            int u = leerVertice(sc, "  Origen: ", graph.vertices);
            int v = leerVertice(sc, "  Destino: ", graph.vertices);
            long w = leerLong(sc, "  Peso: ");

            edges.add(new KruskalMST.Edge(u, v, w));
            PrimMST.addUndirected(adj, u, v, w);
        }

        graph.edges = edges;
        graph.adjacency = adj;
        return graph;
    }

    private static void ejecutarPrim(Scanner sc, GraphData graph) {
        int raiz = leerVertice(sc, "Ingrese el vértice raíz para Prim: ", graph.vertices);
        PrimMST.Result result = PrimMST.prim(graph.adjacency, raiz);
        PrimMST.printResult(result, graph.vertices);
    }

    private static void ejecutarKruskal(GraphData graph) {
        KruskalMST.Result result = KruskalMST.kruskal(graph.vertices, graph.edges);
        KruskalMST.printResult(result, graph.vertices);
    }

    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
                sc.nextLine();
            }
        }
    }

    private static int leerEnteroMayorOIgual(Scanner sc, String mensaje, int minimo) {
        while (true) {
            int valor = leerEntero(sc, mensaje);
            if (valor >= minimo) {
                return valor;
            }
            System.out.println("El valor debe ser mayor o igual a " + minimo + ".");
        }
    }

    private static int leerVertice(Scanner sc, String mensaje, int cantidadVertices) {
        while (true) {
            int valor = leerEntero(sc, mensaje);
            if (valor >= 0 && valor < cantidadVertices) {
                return valor;
            }
            System.out.println("Vértice inválido. Debe estar entre 0 y " + (cantidadVertices - 1) + ".");
        }
    }

    private static long leerLong(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return sc.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero largo.");
                sc.nextLine();
            }
        }
    }
}
