import java.util.Map;
import java.util.HashMap;

public class UndirectedWeightedGraph<E extends Comparable<E>> extends SimpleGraph<E> {
    Map<E, Map<E, Integer>> graph = new HashMap<>();

    @Override
    void add(E x) {
        if(nodes.containsKey(x.toString())) return;
        nodes.put(x.toString(), x);
        graph.put(x, new HashMap<>());
    }

    void addEdge(String current, String neighbour, int weight) {
        if(!nodes.containsKey(current) || !nodes.containsKey(neighbour)) return;
        E currentNode = getNode(current);
        E neighbourNode = getNode(neighbour);
        graph.get(currentNode).put(neighbourNode, weight);
        graph.get(neighbourNode).put(currentNode, weight);
    }

    Map<E, Integer> dijkstra(E s) {
        Map<E, Integer> dist = new HashMap<>();
        BinaryHeap<E> queue = new BinaryHeap<>();
        queue.add(s); dist.put(s, 0);

        while(queue.size() != 0) {
            E u = queue.removeMin();
            for(E v : graph.get(u).keySet()) {
                int c = dist.get(u) + getWeight(u, v);
                dist.putIfAbsent(v, 1000);
                if(c < dist.get(v)) {
                    queue.add(v);
                    dist.put(v, c);
                }
            }
        }

        return dist;
    }

    Map<E, Integer> bellmanFord(E s) {
        Map<E, Integer> dist = new HashMap<>();
        dist.put(s, 0);

        for(int i = 1; i < nodes.size(); i++); {
            updateWeights(dist);
        }

        for(E u : graph.keySet()) {
            for(E v : graph.get(u).keySet()) {
                int c = dist.get(u) + getWeight(u, v);
                if(c < dist.get(v)) {
                    throw new IllegalArgumentException("The graph contains a negative cycle!");
                }
            }
        }

        return dist;
    }

    Map<E, E> prim(String arbitraryNode) {
        BinaryHeap<Relation<E, E>> queue = new BinaryHeap<>();
        Map<E, E> parents = new HashMap<>();
        queue.add(new Relation<E, E>(null, nodes.get(arbitraryNode), 0));

        while(queue.size() != 0) {
            Relation<E, E> minEdge = queue.removeMin();
            E child = minEdge.child, parent = minEdge.parent;
            if(!parents.containsKey(child)) {
                parents.put(child, parent);
                for(E neighbour : graph.get(child).keySet()) {
                    queue.add(
                        new Relation<E, E>(child, neighbour, graph.get(child).get(neighbour))
                    );
                }
            }
        }

        return parents;
    }

    class Relation<K, L> implements Comparable<Relation<K, L>> {
        K parent; 
        L child; 
        int weight;
        
        Relation(K parent, L child, int weight) {
            this.parent = parent;
            this.child = child;
            this.weight = weight;
        }
    
        @Override
        public int compareTo(Relation<K, L> relation) {
            return this.weight - relation.weight;
        }
    }

    void updateWeights(Map<E, Integer> dist) {
        for(E u : graph.keySet()) {
            dist.putIfAbsent(u, 1000);
            for(E v : graph.get(u).keySet()) {
                dist.putIfAbsent(v, 1000);
                int c = dist.get(u) + getWeight(u, v);
                if(c < dist.get(v)) {
                    dist.put(v, c);
                }
            }
        }
    }

    int getWeight(E u, E v) {
        return graph.get(u).get(v);
    }

    @Override
    void printStructure() {
        if(nodes.size() == 0) {
            System.out.println("The graph is empty!");
            return;
        }
        for (HashMap.Entry<E, Map<E, Integer>> entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        UndirectedWeightedGraph<String> g = new UndirectedWeightedGraph<>();
        g.add("a");
        g.add("b");
        g.add("c");
        g.add("d");
        g.addEdge("a", "b", 3);
        g.addEdge("a", "c", 3);
        g.addEdge("a", "d", 1);
        g.addEdge("b", "c", 2);
        g.addEdge("b", "d", 3);
        g.addEdge("c", "d", 3);
        
        g.printStructure();

        Map<String, String> spenntre = g.prim("a");
        System.out.println("\n A minimal spanning tree: " + spenntre);

        Map<String, Integer> shortestDijk = g.dijkstra(g.nodes.get("a"));
        System.out.println("\n Dijkstra: "+shortestDijk);
        Map<String, Integer> shortestFord = g.bellmanFord(g.nodes.get("a"));
        System.out.println("\n Bellman Ford: "+shortestFord);
        System.out.println();
    }
}
