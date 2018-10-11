/**
 * IP Packet Handler
 * @author Hunter Dubbs
 * @version 9/21/2018
 * made for CIT360 at PCT
 * 
 * This interface specifies the methods required for a linked list.
 *
 * @param <E> the data type of the linked list.
 */
public interface LinkedListADT<E> {
	
	public void insert(E e, int loc) throws Exception;
	public void insertFirst(E e);
	public void insertLast(E e);
	public E remove(int loc) throws Exception;
	public E removeFirst();
	public E removeLast();
	public E get(int loc) throws Exception;
	public E getFirst();
	public E getLast();
	public int getSize();
	public boolean isEmpty();
	public boolean contains(E e);
	public int position(E e);
	public void replace(E e, E val);
	public void recycleNode(SLLNode<E> node);
	public SLLNode<E> reuseNode(E e);

}
