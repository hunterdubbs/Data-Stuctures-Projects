package sample;

import java.util.Arrays;

public class ArrayQueue<E>{

    private static int capacity = 4;
    private E[] queue;

    private int front;
    private int back;
    private int size;		//number of elements inn queue

    public ArrayQueue() {
        this(capacity);
    }

    public ArrayQueue(int capacity) {
        queue = (E[]) new Object[capacity];
        this.capacity = capacity;
        front = back = -1;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Object e) {
        if (size == 0) {
            queue[0] = (E)e;
            front = back = 0;
        }
        else {
            if(size == capacity) {
                expandArray();
            }
            back = (back + 1) % capacity;
            queue[back] = (E)e;
        }

        size++;
    }

    public E peek() {
        if (isEmpty())
            return null;
        return queue[front];
    }

    public E dequeue() {
        if(isEmpty())
            return null;
        E temp = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;
        return temp;
    }

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

    @Override
    public String toString() {
        return "ArrayQueue [queue=" + Arrays.toString(queue) + "]";
    }
}
