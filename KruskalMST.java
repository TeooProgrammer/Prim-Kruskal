import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

    public static class Edge implements Comparable<Edge> {
        public final int u;
        public final int v;
        public final long w;

        public Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge other) {
            return Long.compare(this.w, other.w);
        }
    }

    public static class Result {
        public final List<Edge> edges;
        public final long totalWeight;
        public final boolean connected;

        public Result(List<Edge> edges, long totalWeight, boolean connected) {
            this.edges = edges;
            this.totalWeight = totalWeight;
            this.connected = connected;
        }
    }
    
// Estructura de datos para manejar conjuntos disjuntos con compresión de caminos.

    public static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) {
                return false;
            }

            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }

// Implementación del algoritmo de Kruskal utilizando Union-Find.
// Construye el MST seleccionando aristas de menor peso sin formar ciclos.

    public static Result kruskal(int n, List<Edge> edges) {
        List<Edge> sorted = new ArrayList<>(edges);
        Collections.sort(sorted);

        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();
        long totalWeight = 0;

        for (Edge e : sorted) {
            if (uf.union(e.u, e.v)) {
                mst.add(e);
                totalWeight += e.w;
            }
        }

        boolean connected = mst.size() == Math.max(0, n - 1);
        return new Result(mst, totalWeight, connected);
    }


    private static String vertexName(int v) {
        if (v >= 0 && v < 26) {
            return String.valueOf((char) ('A' + v));
        }
        return "V" + v;
    }

    public static void printResult(Result result, int vertexCount) {
        System.out.println("========================================");
        System.out.println("Algoritmo: Kruskal");
        System.out.println("Aristas seleccionadas:");

        if (result.edges.isEmpty()) {
            System.out.println("  (sin aristas)");
        } else {
            for (Edge edge : result.edges) {
                System.out.println("  " + vertexName(edge.u) + " - " + vertexName(edge.v) + " (peso: " + edge.w + ")");
            }
        }

        System.out.println("Costo total: " + result.totalWeight);
        if (result.connected && result.edges.size() == Math.max(0, vertexCount - 1)) {
            System.out.println("Estado: árbol de expansión mínima válido.");
        } else {
            System.out.println("Estado: el grafo no es conexo; la salida corresponde a un bosque de expansión mínima.");
        }
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        int n = 4;

        List<Edge> edges = List.of(
            new Edge(0, 1, 1),
            new Edge(1, 2, 2),
            new Edge(0, 2, 3),
            new Edge(1, 3, 3),
            new Edge(0, 3, 4),
            new Edge(2, 3, 5)
        );

        Result result = kruskal(n, edges);
        printResult(result, n);
    }
}