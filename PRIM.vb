PRIM(G, r)

    para cada vértice u ∈ V(G) hacer
        key[u] ← ∞
        pi[u]  ← NIL

    key[r] ← 0

    Q ← V(G)   // cola de prioridad mínima ordenada por key

    mientras Q ≠ ∅ hacer
        u ← EXTRACT-MIN(Q)

        para cada v ∈ Adj(u) hacer
            si v ∈ Q y w(u, v) < key[v] entonces
                pi[v]  ← u
                key[v] ← w(u, v)
                DECREASE-KEY(Q, v, key[v])

    devolver pi


EXTRACT-MIN(Q)

    u ← elemento de Q con menor key
    eliminar u de Q
    devolver u


DECREASE-KEY(Q, v, nuevoValor)

    key[v] ← nuevoValor
    reordenar Q para mantener la propiedad de mínimo