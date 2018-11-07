package sample;

import java.util.Arrays;

/**
 * Maze Assignment
 * @author CIT360-02
 * @version 11/3/2018
 * @param <E> the object type that the ArrayQueue will store
 *
 * This is the implementation of the queue data structure that we built in class.
 * It uses an array to store objects of the specified type.
 */
public class ArrayQueue<E>{

    //the default initial capacity of the array
    private static int capacity = 4;
    private E[] queue;

    private int front;
    private int back;
    private int size;		//number of elements inn queue

    /**
     * Generate a new ArrayQueue with the default capacity.
     */
    public ArrayQueue() {
        this(capacity);
    }

    /**
     * Generate a new ArrayQueue with the specified capacity.
     * @param capacity the capacity of the queue
     */
    public ArrayQueue(int capacity) {
        queue = (E[]) new Object[capacity];
        this.capacity = capacity;
        front = back = -1;
        size = 0;
    }

    /**
     * Returns the size of the queue.
     * @return the size of the queue.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the queue is empty.
     * @return whether or not the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * This method adds the object to the end of the queue.
     * @param e the object to be added
     */
    public void enqueue(Object e) {
        if (size == 0) {
            queue[0] = (E)e;
            front = back = 0;
        }
        else { //the underlying array might have to be expanded to fit all the elements
            if(size == capacity) {
                expandArray();
            }
            back = (back + 1) % capacity;
            queue[back] = (E)e;
        }

        size++;
    }

    /**
     * Gets the element at the front of the queue without removing it.
     * @return the element at the front of the queue
     */
    public E peek() {
        if (isEmpty())
            return null;
        return queue[front];
    }

    /**
     * Gets the element at the front of the queue and removes it.
     * @return the element at the front of the queue
     */
    public E dequeue() {
        if(isEmpty())
            return null;
        E temp = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;
        return temp;
    }

    /**
     * Increases the size of the underlying array in order to fit more objects into the queue.
     */
    private void expandArray() {E[] temp  = (E[]) new Object[2 * capacity];
        int counter = front;
        for(int i = 0; i < size; i++) {
            temp[i] = queue[counter];
            counter = (counter + 1) % capacity;
        }
        queue = temp;
        capacity = 2 * capacity;
        front = 0;
        back = size - 1;

    }

    /**
     * This method creates and returns a string representation of the queue.
     * @return the string representation of the queue
     */
    @Override
    public String toString() {
        return "ArrayQueue [queue=" + Arrays.toString(queue) + "]";
    }
}
