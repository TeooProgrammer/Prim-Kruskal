import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Arrays;

/**
 * Implementación de PRIM(G, r) usando PriorityQueue.
 * Solo se importan las clases necesarias.
 */
public class PrimMST {

    // Arista (u -> to) con peso
    static class Edge {
        int to;
        long w;

        Edge(int to, long w) {
            this.to = to;
            this.w = w;
        }
    } //NO

    // Nodo para la cola de prioridad
    static class Node implements Comparable<Node> {
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
    } //NO

    /**
     * PRIM(G, r)
     *
     * @param adj lista de adyacencia
     * @param r vértice raíz
     * @return arreglo pi (padres del MST)
     */
    public static int[] prim(List<Edge>[] adj, int r) {
        int n = adj.length; // 1
        long[] key = new long[n]; //1
        int[] pi = new int[n]; //1
        boolean[] inQ = new boolean[n]; //1

        // Inicialización
        Arrays.fill(key, Long.MAX_VALUE); //n
        Arrays.fill(pi, -1); // n
        key[r] = 0; //1

        PriorityQueue<Node> pq = new PriorityQueue<>(); //1

        // Q = V(G)
        for (int u = 0; u < n; u++) {
            inQ[u] = true;
            pq.add(new Node(u, key[u]));
        }

        // mientras Q ≠ ∅
        while (!pq.isEmpty()) {

            // EXTRACT-MIN(Q)
            Node cur = pq.poll();
            int u = cur.v;

            // Lazy deletion
            if (!inQ[u] || cur.key != key[u]) {
                continue;
            }

            inQ[u] = false;

            // para cada v ∈ Adj(u)
            for (Edge e : adj[u]) {
                int v = e.to;

                if (inQ[v] && e.w < key[v]) {
                    pi[v] = u;
                    key[v] = e.w;

                    // Simula DECREASE-KEY
                    pq.add(new Node(v, key[v]));
                }
            }
        }

        return pi;
    }

    // Método auxiliar para grafo no dirigido
    private static void addUndirected(List<Edge>[] adj, int u, int v, long w) {
        adj[u].add(new Edge(v, w));
        adj[v].add(new Edge(u, w));
    }

    // Ejemplo mínimo
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

        int[] pi = prim(adj, 0);

        System.out.println(Arrays.toString(pi));
    }
}