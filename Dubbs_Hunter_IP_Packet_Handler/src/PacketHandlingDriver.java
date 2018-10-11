import java.util.concurrent.locks.ReentrantLock;

/**
 * IP Packet Handler
 * @author Hunter Dubbs
 * @version 9/21/2018
 * made for CIT360 at PCT
 * 
 * This program simulates the processing of data packets by a "consumer" 
 * sent from three "producers" that this program also simulates. Upon 
 * receiving the packets, the program stores them in a single linked list
 * acting as a queue. Those packets are then processed first-in, first-out.
 * The metadata from the packets is stored in "activity.log", and the packets
 * sent from IP addresses ranging from 0 to 99 display their contents, simulated
 * by a String, on the console. This program is continued to run for fifteen
 * seconds, but the value can be changed to have different run-times. 
 */
public class PacketHandlingDriver {
	
	//the run-time parameter, in seconds
	private final static int RUNTIME = 15;

	/**
	 * The main method for the program. It creates the three producer threads
	 * and the consumer thread.
	 * @param args program arguments
	 * @throws Exception runtime exceptions
	 */
	public static void main(String[] args) throws Exception {
		
		//the queue that packets are stored in before they are processed
		LinkedList<Packet> packetQueue = new LinkedList<Packet>();
		//this lock provides concurrency control
		ReentrantLock threadLock = new ReentrantLock();
		
		new Producer("Producer 1", packetQueue, threadLock, RUNTIME);
		new Producer("Producer 2", packetQueue, threadLock, RUNTIME);
		new Producer("Producer 3", packetQueue, threadLock, RUNTIME);
		/*
		 * The consumer runs slightly longer than the other threads to ensure
		 * that all packets have been processed before the program terminates.
		 */
		new Consumer("Consumer", packetQueue, threadLock, RUNTIME + 1);
	}

}
