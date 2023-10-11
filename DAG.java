import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DAG<E> extends SimpleGraph<E> {
    @Override
    void addEdge(String current, String neighbour) {
        if(!nodes.containsKey(current) || !nodes.containsKey(neighbour)) return; 
        graph.get(getNode(current)).add(getNode(neighbour));
    }
    
    //Depth first topological sorting
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

    //Depth first search adjusted for dfsTopSort
    void dfsVisit(E u, Set<E> visited, Stack<E> stack) {
        visited.add(u);
        for(E v : graph.get(u)) {
            if(!visited.contains(v)) {
                dfsVisit(v, visited, stack);
            }            
        }
        stack.push(u);
        System.out.println(stack);
    }
}
