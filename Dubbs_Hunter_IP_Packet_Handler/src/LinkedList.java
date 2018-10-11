
//needs toString method
public class LinkedList<E> implements LinkedListADT<E>{

	private SLLNode<E> head, tail;
	private int size;
	
	public LinkedList(){
		super();
		size = 0;
		head = null;
		tail = null;
	}
	
	public void insert(E e, int loc) throws Exception {
		if(loc > size) {
			throw new Exception("Out of bounds");
		}
		else if(isEmpty() || loc == 0) {
			insertFirst(e);
		}
		else if(loc == size) {
			insertLast(e);
		}
		else {
			SLLNode<E> newNode = new SLLNode<E>(e);
			SLLNode<E> tempNode = head;
			for(int i = 0; i < loc - 1; i++) {
				tempNode = tempNode.getNextNode();
			}
			newNode.setNextNode(tempNode.getNextNode());
			tempNode.setNextNode(newNode);
			size++;
		}
	}

	public void insertFirst(E e) {
		SLLNode<E> newNode = new SLLNode<E>(e);
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

	public void insertLast(E e) {
		SLLNode<E> newNode = new SLLNode<E>(e);
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

	@Override
	public E remove(int loc) throws Exception { 
		E data;
		if(isEmpty() || loc > size) {
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
			tempNode.setNextNode(tempNode.getNextNode().getNextNode());
			return data;
		}
	}

	@Override
	public E removeFirst() {
		E data;
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
		size--;
		return data;
	}

	@Override
	public E removeLast() {
		E data;
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
			SLLNode<E> tempNode = new SLLNode<E>();
			while(tempNode.getNextNode() != tail) {
				tempNode = tempNode.getNextNode();
			}
			tail = tempNode;
			tail.setNextNode(null);
		}
		size--;
		return data;
	}

	@Override
	public E get(int loc) {
		SLLNode<E> tempNode = head;
		for(int i = 0; i < loc; i++) {
			tempNode = tempNode.getNextNode();
		}
		return tempNode.getData();
	}

	@Override
	public E getFirst() {
		return head.getData();
	}

	@Override
	public E getLast() {
		return tail.getData();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int location(E e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void replace(E newE, E oldE) {
		// TODO Auto-generated method stub
		
	}

}
