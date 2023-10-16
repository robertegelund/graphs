import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class DirectedAcyclicGraph<E extends Comparable<E>> extends DirectedWeightedGraph<E> {
    @Override
    void addEdge(String current, String neighbour, int weight) {
        if(!nodes.containsKey(current) || !nodes.containsKey(neighbour)) return;
        E currentNode = getNode(current);
        E neighbourNode = getNode(neighbour);
        graph.get(currentNode).put(neighbourNode, weight);
    }

    Stack<E> dfsTopSort() {
        Stack<E> stack = new Stack<>();
        Set<E> visited = new HashSet<>();
        for(E u : nodes.values()) {
            if(!visited.contains(u)) {
                dfsVisit(u, visited, stack);

            }        
        }        
        if(stack.size() != nodes.size()) {
            throw new IllegalArgumentException("The graph contains a cycle, and cannot be toplogically sorted!");
        }
        
        return stack;    
    }
    
    void dfsVisit(E u, Set<E> visited, Stack<E> stack) {
        visited.add(u);
        for(E v : graph.get(u).keySet()) {
            if(!visited.contains(v)) {
                dfsVisit(v, visited, stack);
            }           
        }
        stack.push(u); //In Java, the last index implies the top of the stack
    }

    Map<E, Integer> shortestPaths(E s) {
        Map<E, Integer> dist = new HashMap<>();
        dist.put(s, 0);
        Stack<E> sorted = dfsTopSort();

        while(!sorted.isEmpty()) {
            E u = sorted.pop();
            dist.putIfAbsent(u, 1000);
            for(E v : graph.get(u).keySet()) {
                int c = dist.get(u) + getWeight(u, v);
                dist.putIfAbsent(v, 1000);
                if(c < dist.get(v)) {
                    dist.put(v, c);
                }
            }
        }

        return dist;
    }


    public static void main(String[] args) {
        DirectedAcyclicGraph<String> dag = new DirectedAcyclicGraph<>();
        dag.add("a");
        dag.add("b");
        dag.add("c");
        dag.add("d");
        dag.add("e");
        dag.add("f");
        dag.add("g");
        dag.addEdge("a", "b", 1);
        dag.addEdge("b", "d", 3);
        dag.addEdge("c", "d", 2);
        dag.addEdge("a", "c", 4);
        dag.addEdge("d", "e", 1);
        dag.addEdge("e", "f", 2);
        dag.addEdge("b", "g", 2);

        dag.printStructure();

        Map<String, Integer> shortest = dag.shortestPaths("a");
        System.out.println(shortest);

    }

}
