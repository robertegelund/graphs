import java.util.Map;
import java.util.HashMap;

public class WeightedGraph<E extends Comparable<E>> extends SimpleGraph<E> {
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
                dist.compute(v, (key, val) -> {
                    if (val == null) {
                        return c;
                    } else {
                        int min = Math.min(val, c);
                        if(min < val) queue.add(v); 
                        return min;
                    }
                });
            }
        }

        return dist;
    } 

    Map<E, Integer> bellmanFord(E s) {
        Map<E, Integer> dist = new HashMap<>();
        dist.put(s, 0);

        for(int i = 0; i < nodes.size(); i++); {
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

    void updateWeights(Map<E, Integer> dist) {
        for(E u : graph.keySet()) {
            for(E v : graph.get(u).keySet()) {
                int c = dist.get(u) + getWeight(u, v);
                dist.compute(v, (key, val) -> {
                    if (val == null) {
                        return c;
                    } else {
                        return Math.min(val, c);
                    }
                });
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
        WeightedGraph<String> g = new WeightedGraph<>();
        g.add("a");
        g.add("b");
        g.add("c");
        g.addEdge("a", "b", 3);
        g.addEdge("a", "c", 1);
        g.addEdge("b", "c", 2);
        g.printStructure();

        Map<String, Integer> shortestPath = g.dijkstra(g.nodes.get("a"));
        System.out.println(shortestPath);
    }
}
