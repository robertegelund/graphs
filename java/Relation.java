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
