import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMST {

    public static class Edge {
        int to;
        long w;

        Edge(int to, long w) {
            this.to = to;
            this.w = w;
        }
    }

    private static class Node implements Comparable<Node> {
        int v;
        long key;

        Node(int v, long key) {
            this.v = v;
            this.key = key;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.key, other.key);
        }
    }

    public static class MSTEdge {
        public final int from;
        public final int to;
        public final long weight;

        public MSTEdge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static class Result {
        public final List<MSTEdge> edges;
        public final long totalWeight;
        public final boolean connected;
        public final int root;//nodo inicial del arbol en formación

        public Result(List<MSTEdge> edges, long totalWeight, boolean connected, int root) {
            this.edges = edges;
            this.totalWeight = totalWeight;
            this.connected = connected;
            this.root = root; 
        }
    }

// Implementación del algoritmo de Prim utilizando cola de prioridad.
// Calcula el árbol de expansión mínima desde un vértice raíz.

    public static Result prim(List<Edge>[] adj, int r) {
        int n = adj.length;
        long[] key = new long[n];
        int[] parent = new int[n];
        boolean[] inQ = new boolean[n];

        Arrays.fill(key, Long.MAX_VALUE);
        Arrays.fill(parent, -1);
        key[r] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int u = 0; u < n; u++) {
            inQ[u] = true;
            pq.add(new Node(u, key[u]));
        }

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.v;

            if (!inQ[u] || cur.key != key[u]) {
                continue;
            }

            inQ[u] = false;

            for (Edge e : adj[u]) {
                int v = e.to;
                if (inQ[v] && e.w < key[v]) {
                    parent[v] = u;
                    key[v] = e.w;
                    pq.add(new Node(v, key[v]));
                }
            }
        }

        List<MSTEdge> mstEdges = new ArrayList<>();
        long totalWeight = 0;
        boolean connected = true;

        for (int v = 0; v < n; v++) {
            if (v == r) {
                continue;
            }
            if (parent[v] == -1) {
                connected = false;
                continue;
            }
            mstEdges.add(new MSTEdge(parent[v], v, key[v]));
            totalWeight += key[v];
        }

        return new Result(mstEdges, totalWeight, connected, r);
    }

    public static void addUndirected(List<Edge>[] adj, int u, int v, long w) {
        adj[u].add(new Edge(v, w));
        adj[v].add(new Edge(u, w));
    }

    public static String vertexName(int index) {
        if (index >= 0 && index < 26) {
            return String.valueOf((char) ('A' + index));
        }
        return "V" + index;
    }

    public static void printResult(Result result, int vertexCount) {
        System.out.println("========================================");
        System.out.println("Algoritmo: Prim");
        System.out.println("Raíz elegida: " + vertexName(result.root));
        System.out.println("Aristas seleccionadas:");

        if (result.edges.isEmpty()) {
            System.out.println("  (sin aristas)");
        } else {
            for (MSTEdge edge : result.edges) {
                System.out.println("  " + vertexName(edge.from) + " - " + vertexName(edge.to) + " (peso: " + edge.weight + ")");
            }
        }

        System.out.println("Costo total: " + result.totalWeight);
        if (result.connected && result.edges.size() == Math.max(0, vertexCount - 1)) {
            System.out.println("Estado: árbol de expansión mínima válido.");
        } else {
            System.out.println("Estado: el grafo no es conexo; la salida corresponde al árbol alcanzable desde la raíz.");
        }
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        int n = 4;

        @SuppressWarnings("unchecked")
        List<Edge>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        addUndirected(adj, 0, 1, 1);
        addUndirected(adj, 0, 2, 4);
        addUndirected(adj, 1, 2, 2);
        addUndirected(adj, 1, 3, 5);
        addUndirected(adj, 2, 3, 3);

        Result result = prim(adj, 0);
        printResult(result, n);
    }
}