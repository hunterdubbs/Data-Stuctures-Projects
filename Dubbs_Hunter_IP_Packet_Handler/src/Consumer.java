import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;

/**
 * IP Packet Handler
 * @author Hunter Dubbs
 * @version 9/21/2018
 * made for CIT360 at PCT
 * 
 * This class implements a Thread for the Consumer, which pulls
 * Packet objects out of the queue and processes them. The metadata 
 * from each Packet object is stored in "activity.log", and the Packet
 * objects with from attributes ranging from 0 to 99 display their contents, 
 * simulated by a String, on the console.This thread will only run for 
 * the specified duration of time. 
 *
 */
public class Consumer implements Runnable{

	private String name;
	private LinkedList<Packet> queue;
	private ReentrantLock lock;
	private Thread thread;
	private Packet packet;
	private PrintWriter pw;
	private FileOutputStream fout;
	private long maxRunTime;
	
	/**
	 * This is the full constructor for the Consumer object. It creates
	 * a new thread that relies on a shared ReentrantLock to prevent
	 * concurrent modification problems.
	 * @param name the name of the thread
	 * @param queue the queue to pull from
	 * @param lock the lock to use
	 * @param maxRunTime the approximate runtime in seconds.
	 */
	public Consumer(String name, LinkedList<Packet> queue, ReentrantLock lock, int maxRunTime) {
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
		//this checks to see if it has already been running for the maxRunTime
		while(System.currentTimeMillis() < maxRunTime) {
			//this attempts to lock the queue before proceeding
			if(lock.tryLock()) {
				try {
					if(!queue.isEmpty()) {
						//the first Packet is removed and processed
						packet = queue.removeFirst();
						if(packet.getFrom() <= 99) {
							System.out.println("Source: " + packet.getFrom() + "\tData: " + packet.getData());
						}
						try {
							//the metadata is written to "activity.log"
							fout = new FileOutputStream("activity.log", true);
							pw = new PrintWriter(fout);
							pw.println(packet.getFrom() + "," + packet.getTo() + "," + packet.getTime());
						} 
						catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						finally {
							pw.close();
						}
					}
				}
				finally {
					/*
					 * the lock is released after each iteration to allow other 
					 * threads a chance to access the queue
					 */
					lock.unlock();
				}
			}
		}
	}

}
