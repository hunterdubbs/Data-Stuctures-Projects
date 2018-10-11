
public class SLLNode<E>{

	private E data;
	private SLLNode<E> nextNode;
	
	//change these to try to use existing nodes first
	public SLLNode(E data){
		this.data = data;
	}
	
	public SLLNode() {
		
	}
	
	public void cleanNode() {
		data = null;
		nextNode = null;
	}
	
	public E getData() {
		return data;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
	public SLLNode<E> getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(SLLNode<E> nextNode) {
		this.nextNode = nextNode;
	}
}
