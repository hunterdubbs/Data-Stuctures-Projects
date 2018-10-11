import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable{

	private String name;
	private LinkedList<Packet> queue;
	private ReentrantLock lock;
	
	public Producer(String name, LinkedList<Packet> queue, ReentrantLock lock) {
		super();
		this.name = name;
		this.queue = queue;
		this.lock = lock;
	}

	@Override
	public void run() {
		
	}

}
