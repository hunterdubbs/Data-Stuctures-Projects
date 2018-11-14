import java.util.Arrays;

/**
 * Exam 2
 *
 * @author Hunter Dubbs
 * @version 11/13/2018
 * @param <E> the data type to be stored in the MinHeap
 *
 * This class creates a MinHeap tree data object and stores it
 * using an array. As the array fills up, the size is doubled to
 * accomendate more entries. This class also can perform a heap sort.
 */
public class MinHeap<E extends Comparable> {

    private E[] heap;
    private int size;

    //the size that the array starts at by default
    private static int DEFAULT_SIZE = 4;

    /**
     * The default constructor uses the DEFAULT_SIZE parameter.
     * Operational Complexity -> O(1)
     */
    public MinHeap(){
        size = 0;
        heap = (E[]) new Comparable[DEFAULT_SIZE];
    }

    /**
     * This constructor allows you to specify the initial size of the array.
     * @param initSize the initial size of the array
     * Operational Complexity -> O(1)
     */
    public MinHeap(int initSize){
        size = 0;
        heap = (E[]) new Comparable[initSize];
    }

    /**
     * This constructor allows the contents of the array and
     * the size to be specified. It is primarily used to duplicate
     * the array for the sort() method.
     * @param arr the filled array
     * @param size the size of the heap
     * Operational Complexity -> O(1)
     */
    public MinHeap(E[] arr, int size){
        heap = arr;
        this.size = size;
    }

    /**
     * This method returns the size of the MinHeap.
     * @return the size of the MinHeap
     * Operational Complexity -> O(1)
     */
    public int size(){
        return size;
    }

    /**
     * This method returns whether or not the MinHeap is empty.
     * @return whether or noth the MinHeap is empty
     * Operational Complexity -> O(1)
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * This method creates a new array, twice the size as the
     * previous one, copies the values to it, and switches over to it.
     * Operational Complexity -> O(n)
     */
    private void expandArray(){
        E[] newHeap = (E[]) new Comparable[size * 2];
        for(int i = 0; i < heap.length; i++){
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /**
     * This method inserts a value into the MinHeap and then
     * adjusts it in order to ensure that all nodes are still less
     * than their child nodes.
     * @param e the value to insert
     * Operational Complexity -> O(logn)
     */
    public void insert(E e){
        if(size == heap.length){
            expandArray();
        }
        int curLoc = size;
        heap[curLoc] = e;
        while(curLoc != 0 && e.compareTo(heap[(curLoc - 1) / 2]) < 0){
            heap[curLoc] = heap[(curLoc - 1) / 2];
            heap[(curLoc - 1) / 2] = e;
            curLoc = (curLoc - 1) / 2;
        }
        size++;
    }

    /**
     * This method removes a node at the specified index and
     * rebalances the MinHeap afterwards.
     * @param index the index of the node to be removed
     * @return the value of the removed node
     * Operational Complexity -> O(logn)
     */
    public E remove(int index) {
        if(index > size - 1){
            return null;
        }
        E temp;
        E outTemp;
        int i = index;
        outTemp = heap[i];
        heap[i] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        //filters the number down -> O(logn)
        while ((size > i * 2 + 1 && heap[i * 2 + 1].compareTo(outTemp) > 0)
                || (size > i * 2 + 2 && heap[i * 2 + 2].compareTo(outTemp) > 0)) {
            temp = heap[i];
            if (size == i * 2 + 2 || (size > i * 2 + 1 && heap[i * 2 + 1].compareTo(heap[i * 2 + 2]) < 0)) {
                heap[i] = heap[i * 2 + 1];
                heap[i * 2 + 1] = temp;
                i = i * 2 + 1;
            } else {
                heap[i] = heap[i * 2 + 2];
                heap[i * 2 + 2] = temp;
                i = i * 2 + 2;
            }
        }
        return outTemp;
    }

    /**
     * This method locates and removes the specified value from
     * the MinHeap. Afterwards, it rebalances the MinHeap. If the
     * value was not found, it will return null.
     * @param e the value to be removed
     * @return the removed value
     * Operational Complexity -> O(n)
     */
    public E remove(E e){
        E temp;
        //since a MinHeap is not ordered, it is necessary to check every
        //location for the desired element -> O(n)
        for(int i = 0; i < heap.length; i++){
            if(heap[i].compareTo(e) == 0){
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                //filters the number down -> O(logn)
                while((size > i * 2 && heap[i * 2 + 1].compareTo(e) > 0) || (size > i * 2 + 1 && heap[i * 2 + 2].compareTo(e) > 0)){
                    temp = heap[i];
                    if(size == i * 2 + 2 || (size > i * 2 + 2 && heap[i * 2 + 1].compareTo(heap[i * 2 + 2]) < 0)){
                        heap[i] = heap[i * 2 + 1];
                        heap[i * 2 + 1] = temp;
                        i = i * 2 + 1;
                    }else{
                        heap[i] = heap[i * 2 + 2];
                        heap[i * 2 + 2] = temp;
                        i = i * 2 + 2;
                    }
                }
                return e;
            }
        }
        return null;
    }

    /**
     * This method creates a new instance of the MinHeap and
     * sorts it, outputting the results in an array.
     * @return an array of the sorted values
     * Operational Complexity -> O(nlogn)
     */
    public E[] sort(){
        MinHeap<E> tempHeap = new MinHeap(heap.clone(), size);
        E[] sortedHeap = (E[]) new Comparable[size];
        for(int i = 0; i < sortedHeap.length; i++){
            sortedHeap[i] = tempHeap.remove(0);
        }
        return sortedHeap;
    }

    /**
     * This method creates a String representation of the MinHeap
     * @return the string representation
     * Operational Complexity -> O(n)
     */
    @Override
    public String toString() {
        return "MinHeap{" +
                "heap=" + (heap == null ? null : Arrays.asList(heap)) +
                ", size=" + size +
                '}';
    }
}
