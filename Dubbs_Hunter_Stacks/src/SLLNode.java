/**
 * Simple Stack Assignment
 * @author Hunter Dubbs
 * @version 10/11/2018
 * made for CIT360 at PCT
 *
 * Source: This class has been repurposed from the IP Packet Handler Assignment.
 *
 * This class implements a node to be used by the SingleLinkedList. Each node
 * points forward to the next sequential node in the LinkedList.
 *
 * @param <E> the data type to be stored in the SLLNode
 */
public class SLLNode<E>{

    private E data;
    private SLLNode<E> nextNode;

    /**
     * This is the constructor for SLLNode that preloads it
     * with the data.
     * @param data the object to be stored
     */
    public SLLNode(E data){
        this.data = data;
    }

    /**
     * This is the default constructor for SLLNode.
     */
    public SLLNode() {
        this.data = null;
        this.nextNode = null;
    }

    /**
     * This method sets all of the SLLNode data attributes to null.
     */
    public void cleanNode() {
        data = null;
        nextNode = null;
    }

    /**
     * This method returns the object stored in the SLLNode.
     * @return the object
     */
    public E getData() {
        return data;
    }

    /**
     * This method sets the object to be stored in the SLLNode.
     * @param data the object
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * This method returns the next sequential SLLNode.
     * @return the next SLLNode
     */
    public SLLNode<E> getNextNode() {
        return nextNode;
    }

    /**
     * This method sets the link for the next SLLNode in the sequence.
     * @param nextNode the next SLLNode
     */
    public void setNextNode(SLLNode<E> nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * This method returns a String representation of the data stored
     * in the SLLNode.
     * @return the String representation
     */
    @Override
    public String toString() {
        return data.toString();
    }

}