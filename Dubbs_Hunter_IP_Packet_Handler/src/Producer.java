import java.sql.Timestamp;
import java.util.concurrent.locks.ReentrantLock;

/**
 * IP Packet Handler
 * @author Hunter Dubbs
 * @version 9/21/2018
 * made for CIT360 at PCT
 * 
 * This class implements a Thread for a Producer. Each Producer
 * has a configurable chance of producing a Packet and adding
 * it to the queue every 100 milliseconds. It will run for the
 * specified duration of time.
 *
 */
public class Producer implements Runnable{

	/*
	 * This setting determines how likely the thread is to 
	 * produce a Packet. 0 will prevent any from being produced
	 * and 1 will always produce Packet objects.
	 */
	private final static double MSG_CHANCE = 0.9;
	//the maximum IP address
	private final static int IP_ADDRESS_RANGE_MAX = 255;
	
	private String name;
	private LinkedList<Packet> queue;
	private ReentrantLock lock;
	private Thread thread;
	private long maxRunTime;
	
	/**
	 * This is the full constructor for Producer. It creates
	 * a new thread that relies on a shared ReentrantLock to prevent
	 * concurrent modification problems.
	 * @param name the name of the thread
	 * @param queue the queue to pull from
	 * @param lock the lock to use
	 * @param maxRunTime the approximate runtime in seconds.
	 */
	public Producer(String name, LinkedList<Packet> queue, ReentrantLock lock, int maxRunTime) {
		super();
		this.name = name;
		this.queue = queue;
		this.lock = lock;
		this.maxRunTime = System.currentTimeMillis() + maxRunTime * 1000;
		thread = new Thread(this, this.name);
		thread.start();
	}

	/**
	 * This method is run by the thread. 
	 */
	@Override
	public void run() {
		//this makes sure that it has not already been running for maxRunTime
		while(System.currentTimeMillis() < maxRunTime) {
			//this tries to lock the queue if the RNG allows it this iteration
			if(Math.random() < MSG_CHANCE && lock.tryLock()) {
				try {
					//inserts the Packet at the end of the queue
					queue.insertLast(new Packet(generateString(), generateAddress(), generateAddress(), 
							new Timestamp(System.currentTimeMillis()).toString()));
				}
				finally {
					//releases the lock
					lock.unlock();
				}
			}
			try {
				//waits 100 milliseconds to prevent spamming with Packet objects
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method generates a random string between 1 and 80 characters
	 * in length made up of the characters [a-z] to serve as the data 
	 * content for the Packet.
	 * @return a random String
	 */
	private String generateString() {
		String out = "";
		for(int i = 0; i < (int) (Math.random() * 80) + 1; i++) {
			out += (char) ((int) (Math.random() * 26) + 'a');
		}
		return out;
	}
	
	/**
	 * This method generates a random IP address between 0 and the
	 * configured maximum IP address (255 by default).
	 * @return the random IP address
	 */
	private int generateAddress() {
		return (int) (Math.random() * (IP_ADDRESS_RANGE_MAX + 1));
	}

}
