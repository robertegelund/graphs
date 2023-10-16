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
        return stack;
    }

    void dfsVisit(E u, Set<E> visited, Stack<E> stack) {
        visited.add(u);
        for(E v : graph.get(u).keySet()) {
            if(!visited.contains(v)) {
                dfsVisitReversed(v, visited, stack);
            }            
        }
        stack.push(u);
    }

     Stack<E> dfsTopSortReversed() {
        Stack<E> stack = new Stack<>();
        Set<E> visited = new HashSet<>();
        for(E u : nodes.values()) {
            if(!visited.contains(u)) {
                dfsVisitReversed(u, visited, stack);
            }
        }
        return stack;
    }

    void dfsVisitReversed(E u, Set<E> visited, Stack<E> stack) {
        visited.add(u);
        stack.push(u);
        for(E v : graph.get(u).keySet()) {
            if(!visited.contains(v)) {
                dfsVisitReversed(v, visited, stack);
            }            
        }
    }

    Map<E, Integer> shortestPaths(E s) {
        Map<E, Integer> dist = new HashMap<>();
        dist.put(s, 0);
        Stack<E> sorted = dfsTopSortReversed();
        System.out.println(sorted);

        for(E u : sorted) {
            for(E v : graph.get(u).keySet()) {
                dist.putIfAbsent(u, 1000);
                dist.putIfAbsent(v, 1000);
                int c = dist.get(u) + getWeight(u, v);
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
        dag.addEdge("a", "b", 1);
        dag.addEdge("b", "d", 3);
        dag.addEdge("c", "d", 2);
        dag.addEdge("a", "c", 4);

        dag.printStructure();

        Map<String, Integer> shortest = dag.shortestPaths("a");
        System.out.println(shortest);

    }

}
