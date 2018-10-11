import java.util.concurrent.locks.ReentrantLock;

public class PacketHandlingDriver {
	
	

	public static void main(String[] args) {
		
		LinkedList<Packet> packetQueue = new LinkedList<Packet>();
		
		ReentrantLock threadLock = new ReentrantLock();
		new Producer("Producer 1", packetQueue, threadLock);
		new Producer("Producer 2", packetQueue, threadLock);
		new Producer("Producer 3", packetQueue, threadLock);
		//new Consumer("Consumer", packetQueue, threadLock);
	}

}
