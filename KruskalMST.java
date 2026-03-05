import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

    // Arista (u, v) con peso w
    public static class Edge implements Comparable<Edge> {
        public final int u;
        public final int v;
        public final int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.w, other.w); // orden no decreciente por peso
        }

        @Override
        public String toString() {
            return "(" + u + ", " + v + ", " + w + ")";
        }
    }

    // Conjuntos Disjuntos (Union-Find)
    public static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // MAKE-SET(i)
                rank[i] = 0;
            }
        }

        public int find(int x) { // FIND-SET(x)
            if (parent[x] != x)
                parent[x] = find(parent[x]); // compresión de caminos
            return parent[x];
        }

        public boolean union(int a, int b) { // UNION(a, b)
            int ra = find(a);
            int rb = find(b);
            if (ra == rb)
                return false;

            // unión por rango
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

    /**
     * KRUSKAL(G):
     *  - n: número de vértices (0..n-1)
     *  - edges: lista de aristas de G
     * Devuelve las aristas del árbol de expansión mínima
     * (o un bosque de expansión mínima si G es disconexo).
     */
    public static List<Edge> kruskal(int n, List<Edge> edges) {
        List<Edge> sorted = new ArrayList<>(edges);
        Collections.sort(sorted); // ordenar aristas en orden no decreciente según su peso

        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : sorted) { // tomadas en orden no decreciente según su peso
            if (uf.find(e.u) != uf.find(e.v)) {
                mst.add(e);          // A = A ∪ {(u, v)}
                uf.union(e.u, e.v);  // unir componentes
            }
        }

        return mst;
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        int n = 4; // vértices: 0,1,2,3  (A,B,C,D)

        List<Edge> edges = List.of(
            new Edge(0, 1, 1), // A-B
            new Edge(1, 2, 2), // B-C
            new Edge(0, 2, 3), // A-C
            new Edge(1, 3, 3), // B-D
            new Edge(0, 3, 4), // A-D
            new Edge(2, 3, 5)  // C-D
        );

        List<Edge> mst = kruskal(n, edges);
        System.out.println("Aristas del árbol de expansión mínima: " + mst);

        int total = 0;
        for (Edge e : mst)
            total += e.w;

        System.out.println("Peso total: " + total);
    }
}