/**
 * Simple Stack Assignment
 * @author Hunter Dubbs
 * @version 10/11/2018
 * made for CIT360 at PCT
 *
 * This class implements a stack data structure based off
 * of a SingleLinkedList. It contains all the basic methods
 * required to function as a stack.
 *
 * @param <E> the data type of the stack
 */
public class Stack<E extends Comparable<E>> {

    SingleLinkedList<E> stack;

    /**
     * The default constructor for Stack generates a new
     * underlying SingleLinkedList of the same data type.
     */
    public Stack(){
        super();
        stack = new SingleLinkedList<E>();
    }

    /**
     * This method adds the specified element to the top
     * of the stack.
     * @param e the element to be added
     */
    public void push(E e){
        stack.insertFirst(e);
    }

    /**
     * This method removes the top element from the stack
     * and returns it. The possibility of an empty stack is
     * handled by the underlying SingleLinkedList method.
     * @return the top element
     */
    public E remove(){
        return stack.removeFirst();
    }

    /**
     * This method retrieves the object on the top of the stack
     * without removing it. The possibility of an empty stack is
     * handled by the underlying SingleLinkedList method.
     * @return the top element
     */
    public E peek(){
        return stack.getFirst();
    }

    /**
     * This method checks if the stack is empty.
     * @return whether or not the stack is empty
     */
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    /**
     * This method returns the number of elements in
     * the stack.
     * @return the number of elements in the stack
     */
    public int size(){
        return stack.getSize();
    }

    /**
     * This method removes all the elements from the
     * stack without returning any of them.
     */
    public void emptyList(){
        stack.emptyList();
    }

    /**
     * This method returns a String representation of
     * the elements in the stack.
     * @return the elements in the stack
     */
    @Override
    public String toString() {
        return "Stack{ " + stack + " }";
    }
}
