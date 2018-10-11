import java.util.ArrayList;

/**
 * IP Packet Handler
 * @author Hunter Dubbs
 * @version 9/21/2018
 * made for CIT360 at PCT
 * 
 * Source: Professor Golshan class lectures with modifications.
 * 
 * This class is an implementation of a  Single Linked List.
 * It has been modified to allow old, discarded SLLNodes from
 * remove(), removeFirst(), and removeLast() to be cleaned of
 * data and then reused in place of new SLLNodes in insert(),
 * insertFirst(), and insertLast(). This helps optimize the program.
 *
 * @param <E> the data type of the linked list
 */
public class LinkedList<E extends Comparable<E>> implements LinkedListADT<E>{

	private SLLNode<E> head, tail;
	private int size;
	//this ArrayList stores the old SLLNode objects waiting to be reused
	private ArrayList<SLLNode<E>> emptyNodes = new ArrayList<SLLNode<E>>();;
	
	/**
	 * This is the default constructor for LinkedList<E>. It 
	 * creates an empty LinkedList for objects of data type E.
	 */
	public LinkedList(){
		super();
		size = 0;
		head = null;
		tail = null;
	}
	
	/**
	 * This method inserts the provided object into the LinkedList
	 * at the specified index pushing back the elements behind it.
	 * @param e the object to be inserted
	 * @param loc the location to insert the object
	 */
	public void insert(E e, int loc) throws Exception {
		if(loc > size || loc < 0) {
			throw new Exception("Out of bounds");
		}
		else if(isEmpty() || loc == 0) {
			insertFirst(e);
		}
		else if(loc == size) {
			insertLast(e);
		}
		else {
			SLLNode<E> newNode = reuseNode(e);
			SLLNode<E> tempNode = head;
			for(int i = 0; i < loc - 1; i++) {
				tempNode = tempNode.getNextNode();
			}
			newNode.setNextNode(tempNode.getNextNode());
			tempNode.setNextNode(newNode);
			size++;
		}
	}

	/**
	 * This method inserts the provided object at the beginning
	 * of the LinkedList. All other elements will be pushed back.
	 * @param e the object to be inserted
	 */
	public void insertFirst(E e) {
		SLLNode<E> newNode = reuseNode(e);
		if(size == 0) {
			head = newNode;
			tail = newNode;
		}
		else {
			newNode.setNextNode(head);
			head = newNode;
		}
		size++;
	}

	/**
	 * This method inserts the provided object at the end
	 * of the LinkedList.
	 * @param e the object to be inserted
	 */
	public void insertLast(E e) {
		SLLNode<E> newNode = reuseNode(e);
		if(size == 0) {
			head = newNode;
		 	tail = newNode;
		} 
		else {
			tail.setNextNode(newNode);
			tail = newNode;
		}
		size++;
	}

	/**
	 * This method removes the object at the specified location
	 * in the LinkedList. The elements behind it move forward.
	 * @param loc the location of the object to be removed
	 * @return the removed object
	 */
	@Override
	public E remove(int loc) throws Exception { 
		E data;
		if(isEmpty() || loc > size || loc < 0) {
			throw new Exception("unable to perform remove operation");
		}
		else if(size == 1 || loc == 0) {
			return removeFirst();
		}
		else if(loc == size) {
			return removeLast();
		}
		else {
			SLLNode<E> tempNode = head;
			for(int i = 0; i < loc - 1; i++) {
				tempNode = tempNode.getNextNode();
			}
			data = tempNode.getNextNode().getData();
			SLLNode<E> oldNode = tempNode.getNextNode();
			tempNode.setNextNode(tempNode.getNextNode().getNextNode());
			recycleNode(oldNode);
			size--;
			return data;
		}
	}

	/**
	 * This method removes the first object from the LinkedList.
	 * @return the removed object
	 */
	@Override
	public E removeFirst() {
		E data;
		SLLNode<E> oldNode = head;
		if(isEmpty()) {
			return null;
		}
		else if(size == 1) {
			data = head.getData();
			head = null;
			tail = null;
		}
		else {
			data = head.getData();
			head = head.getNextNode();
			
		}
		recycleNode(oldNode);
		size--;
		return data;
	}

	/**
	 * This method removes the last object from the LinkedList.
	 * @return the removed object
	 */
	@Override
	public E removeLast() {
		E data;
		SLLNode<E> oldNode = tail;
		if(isEmpty()) {
			return null;
		}
		else if(size == 1) {
			data = tail.getData();
			head = null;
			tail = null;
		}
		else {
			data = tail.getData();
			SLLNode<E> tempNode = head;
			while(tempNode.getNextNode() != tail) {
				tempNode = tempNode.getNextNode();
			}
			tail = tempNode;
			tail.setNextNode(null);
		}
		recycleNode(oldNode);
		size--;
		return data;
	}

	/**
	 * This method gets the object at the specified location
	 * in the LinkedList.
	 * @param loc the location of the object to be retrieved
	 * @return the retrieved object
	 */
	@Override
	public E get(int loc) {
		SLLNode<E> tempNode = head;
		for(int i = 0; i < loc; i++) {
			tempNode = tempNode.getNextNode();
		}
		return tempNode.getData();
	}

	/**
	 * This method gets the first object in the LinkedList.
	 * @return the retrieved object
	 */
	@Override
	public E getFirst() {
		return head.getData();
	}

	/**
	 * This method gets the last object in the LinkedList.
	 * @return the retrieved object
	 */
	@Override
	public E getLast() {
		return tail.getData();
	}

	/**
	 * This method gets the number of objects stored in the LinkedList.
	 * @return the number of objects
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * This method returns true if the LinkedList is empty.
	 * @return whether or not the LinkedList is empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * This method formats the LinkedList as a String.
	 * @return the String representation
	 */
	@Override
	public String toString() {
		String out = "";
		SLLNode<E> tempNode = head;
		while(tempNode != null) {
			out += tempNode.toString() + "\n";
			tempNode = tempNode.getNextNode();
		}
		return out;
	}

	/**
	 * This method returns true if the specified object is
	 * present somewhere in the LinkedList.
	 * @return whether or not the object is present
	 */
	@Override
	public boolean contains(E e) {
		SLLNode<E> tempNode = head;
		while (tempNode != null){
			if(tempNode.getData().compareTo(e) == 0) {
				return true;
			}
			tempNode = tempNode.getNextNode();
		}
		return false;
	}

	/**
	 * This method locates the index position of the specified
	 * object in the LinkedList. If it is not present, this 
	 * method returns -1.
	 * @return the position of the object
	 */
	@Override
	public int position(E e) {
		SLLNode<E> tempNode = head;
		int curLoc = 0;
		while(tempNode != null) {
			if(tempNode.getData().compareTo(e) == 0) {
				return curLoc;
			}
			curLoc++;
			tempNode = tempNode.getNextNode();
		}
		return -1;
	}

	/**
	 * This method replaces the specified object with another object.
	 * @param oldE the object to be replaced
	 * @param newE the object to replace the old object
	 */
	@Override
	public void replace(E oldE, E newE) {
		int pos = position(oldE);
		if(pos != -1) {
			try {
				remove(pos);
				insert(newE, pos);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * This method removes the data from the specified SLLNode and puts
	 * it in an ArrayList to be used later.
	 * @param node the node to be recycled
	 */
	@Override
	public void recycleNode(SLLNode<E> node) {
		node.cleanNode();
		emptyNodes.add(node);
	}

	/**
	 * This method should be called in favor of outright generating a new
	 * SLLNode. It will first check to see if any recycled SLLNode objects
	 * are stored in the ArrayList, and then use one. If there are none, it
	 * will instead generate a new SLLNode.
	 * @param e the object to be stored in the SLLNode
	 * @return the SLLNode containing the object
	 */
	@Override
	public SLLNode<E> reuseNode(E e) {
		if(!emptyNodes.isEmpty()) {
			SLLNode<E> tempNode = emptyNodes.remove(0);
			tempNode.setData(e);
			return tempNode;
		}
		return new SLLNode<E>(e);
	}
	
	

}
