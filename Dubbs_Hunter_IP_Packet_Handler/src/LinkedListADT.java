
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
	public int location(E e);
	public void replace(E newE, E oldE);

}
