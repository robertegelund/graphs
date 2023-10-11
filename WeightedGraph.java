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

    //TODO: Implement the rest of the Dijkstra method
    Map<E, Integer> dijkstra(E s) {
        Map<E, Integer> dist = new HashMap<>();
        BinaryHeap<E> queue = new BinaryHeap<>();
        queue.add(s); dist.put(s, 0);

        return null;
    } 

    int getWeight(String u, String v) {
        return graph.get(getNode(u)).get(getNode(v));
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
        g.addEdge("a", "b", 2);
        g.addEdge("a", "c", 3);
        g.printStructure();
    }
}
