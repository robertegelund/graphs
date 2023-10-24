public class DirectedWeightedGraph<E extends Comparable<E>> extends UndirectedWeightedGraph<E> {
    
    @Override
    void addEdge(String current, String neighbour, int weight) {
        if(!nodes.containsKey(current) || !nodes.containsKey(neighbour)) return;
        E currentNode = getNode(current);
        E neighbourNode = getNode(neighbour);
        graph.get(currentNode).put(neighbourNode, weight);
    }

}
