package pr2;

import java.util.*;

public class Graph<V> {

    private Map<V, Set<V>> adjacencyList = new HashMap<>();

    // public Graph() {
    //   this.adjacencyList = new HashMap<>();
    //}

    /**
     * Añade el vértice `v` al grafo.
     *
     * @param v vértice a añadir.
     * @return `true` si no estaba anteriormente y `false` en caso contrario.
     */
    public boolean addVertex(V v) {
        if (adjacencyList.containsKey(v)) {
            return false;
        }
        adjacencyList.put(v, new HashSet<>());
        return true;
    }

    /**
     * Añade un arco entre los vértices `v1` y `v2` al grafo. En caso de
     * que no exista alguno de los vértices, lo añade también.
     *
     * @param v1 el origen del arco.
     * @param v2 el destino del arco.
     * @return `true` si no existía el arco y `false` en caso contrario.
     */
    public boolean addEdge(V v1, V v2) {
        if (!adjacencyList.containsKey(v1)) {
            adjacencyList.put(v1, new HashSet<>());
        }
        if (!adjacencyList.containsKey(v2)) {
            adjacencyList.put(v2, new HashSet<>());
        }
        if (adjacencyList.get(v1).contains(v2)) {
            return false;
        }
        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1);
        return true;
    }

    /**
     * Obtiene el conjunto de vértices adyacentes a `v`.
     *
     * @param v vértice del que se obtienen los adyacentes.
     * @return conjunto de vértices adyacentes.
     */
    public Set<V> obtainAdjacents(V v) {
        return adjacencyList.getOrDefault(v, Collections.emptySet());
    }

    /**
     * Comprueba si el grafo contiene el vértice dado.
     *
     * @param v vértice para el que se realiza la comprobación.
     * @return `true` si `v` es un vértice del grafo.
     */
    public boolean containsVertex(V v) {
        return adjacencyList.containsKey(v);
    }
    /**
     * Método `toString()` reescrito para la clase `Grafo.java`.
     * @return una cadena de caracteres con la lista de adyacencia.
     */
    @Override
    public String toString() {
        StringBuilder CString = new StringBuilder();
        for (V v : adjacencyList.keySet()) {
            CString.append(v).append(": ");
            for (V adj : adjacencyList.get(v)) {
                CString.append(adj).append(" ");
            }
            CString.append("\n");
        }
        return CString.toString();
    }
    /**
     * Obtiene, en caso de que exista, el camino más corto entre
     * `v1` y `v2`. En caso contrario, devuelve `null`.
     *
     * @param v1 el vértice origen.
     * @param v2 el vértice destino.
     * @return lista con la secuencia de vértices del camino más corto
     * entre `v1` y `v2`
     **/
    public List<V> shortestPath(V v1, V v2) {
            if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
                return Collections.emptyList();
            }

            Map<V, V> predecessors = new HashMap<>();
            Queue<V> queue = new LinkedList<>();
            Set<V> visited = new HashSet<>();

            queue.add(v1);
            visited.add(v1);

            while (!queue.isEmpty()) {
                V current = queue.poll();

                if (current.equals(v2)) {
                    return reconstructPath(predecessors, v1, v2);
                }

                for (V neighbor : adjacencyList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        predecessors.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }

            return Collections.emptyList();
        }

        private List<V> reconstructPath (Map < V, V > predecessors, V v1, V v2){
            List<V> path = new LinkedList<>();
            for (V at = v2; at != null; at = predecessors.get(at)) {
                path.add(at);
            }
            Collections.reverse(path);

            if (path.get(0).equals(v1)) {
                return path;
            } else {
                return Collections.emptyList();
            }
        }
}