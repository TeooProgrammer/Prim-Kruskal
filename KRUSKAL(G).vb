KRUSKAL(G)

    A ← ∅

    para cada vertex v ∈ V(G) hacer
        MAKE-SET(v)

    ordenar las aristas E(G) en orden creciente según su peso

    para cada arista (u, v) ∈ E(G) en orden no decreciente según su peso hacer
        si FIND-SET(u) ≠ FIND-SET(v) entonces
            A ← A ∪ {(u, v)}
            UNION(u, v)

    devolver A


MAKE-SET(v)

    parent[v] ← v
    rank[v]   ← 0


FIND-SET(v)

    si parent[v] ≠ v entonces
        parent[v] ← FIND-SET(parent[v])   // compresión de caminos

    devolver parent[v]


UNION(u, v)

    ru ← FIND-SET(u)
    rv ← FIND-SET(v)


    si ru = rv entonces
        devolver


    si rank[ru] < rank[rv] entonces
        parent[ru] ← rv
    sino si rank[ru] > rank[rv] entonces
        parent[rv] ← ru
    sino
        parent[rv] ← ru
        rank[ru] ← rank[ru] + 1
