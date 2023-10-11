import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.ArrayDeque;

class SimpleGraph<E> {
    Map<String, E> nodes = new HashMap<>();
    Map<E, Set<E>> graph = new HashMap<>();

    void dfsVisit(E s, Set<E> visited) {
        visited.add(s);
        for(E v : graph.get(s)) {
            if(!visited.contains(v)) {
                dfsVisit(v, visited);
            }
        }
    }

    void bfsVisit(E s, Set<E> visited) {
        visited.add(s);
        Queue<E> queue = new ArrayDeque<>(); queue.add(s);
        String path = "\nPath: " + s;

        while(queue.size() != 0) {
            E u = queue.remove();
            for(E v : graph.get(u)) {
                if(!visited.contains(v)) {
                    path += " > " + v;
                    visited.add(v);
                    queue.add(v);
                }
            }
        }

        System.out.println(path + "\n");
    }

    E getNode(String node) {
        return nodes.get(node); 
    }

    void addEdge(String current, String neighbour) {
        if(!nodes.containsKey(current) || !nodes.containsKey(neighbour)) return;
        E currentNode = getNode(current);
        E neighbourNode = getNode(neighbour);
        graph.get(currentNode).add(neighbourNode);
        graph.get(neighbourNode).add(currentNode);
    }

    void add(E x) {
        if(nodes.containsKey(x.toString())) return;
        nodes.put(x.toString(), x);
        graph.put(x, new HashSet<>());
    }

    void remove(E x) {
        E removedNode = nodes.remove(x);
        if(removedNode != null) return;
        for(Set<E> neighbours : graph.values()) {
            neighbours.remove(x);
        }
        graph.remove(x);
    }

    void printStructure() {
        if(nodes.size() == 0) {
            System.out.println("The graph is empty!");
            return;
        }
        for (HashMap.Entry<E, Set<E>> entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        SimpleGraph<String> g = new SimpleGraph<>();

        g.add("f");
        g.add("d");
        g.addEdge("a", "f");
        g.addEdge("a", "b");
        g.addEdge("a", "c");

        g.printStructure();

        // Set<SimpleGraph<String>> visited = new HashSet<>();
        // // g.bfsVisit(g.getNode("a"), visited);
    }
}