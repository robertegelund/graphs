import java.util.List;
import java.util.ArrayList;

class BinaryHeap<E extends Comparable<E>> {
    List<E> arr = new ArrayList<>();

    public void add(E x) {
        add(arr, x);
    }

    public E removeMin() {
        return removeMin(arr);
    }

    private void add(List<E> arr, E x) {
        int n = arr.size();
        arr.add(x);
        int i = n;

        //While the node has a parent and the node's value is lower
        while(i > 0 && arr.get(i).compareTo(arr.get(parentOf(i))) < 0) {
            E parent = arr.get(parentOf(i));
            arr.set(parentOf(i), arr.get(i));
            arr.set(i, parent);
            i = parentOf(i);
        }
    }

    private E removeMin(List<E> arr) {
        if(arr.size() == 1) return arr.remove(0);

        E x = arr.get(0);
        arr.set(0, arr.remove(arr.size()-1));
        int i = 0;

        //While a parent node has a right child, find the smallest child
        while(rightChild(i) < (arr.size() - 1)) {
            int j = arr.get(leftChild(i)).compareTo(arr.get(rightChild(i))) <= 0 
                    ? leftChild(i) 
                    : rightChild(i);
            
            //If a child, j, is greater than the parent, i, exit the loop
            if(arr.get(j).compareTo(arr.get(i)) > 0) return x;

            //Switch a smaller or equal child with a greater parent
            E parent = arr.get(i);
            arr.set(i, arr.get(j)); 
            arr.set(j, parent);

            //The next parent becomes the smallest child of the previous parent
            i = j;
        }

        if( (leftChild(i) < (arr.size())) && (arr.get(leftChild(i)).compareTo(arr.get(i))) <= 0) {
            E parent = arr.get(i);
            arr.set(i, arr.get(leftChild(i)));
            arr.set(leftChild(i), parent);
        }
        
        return x;
    }
    

    private int parentOf(int i) {
        return (int) Math.floor((i - 1) / 2);
    }

    private int leftChild(int i) {
        return 2*i + 1;
    }

    private int rightChild(int i) {
        return 2*i + 2;
    }

}