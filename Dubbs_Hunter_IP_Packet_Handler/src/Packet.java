/**
 * IP Packet Handler
 * @author Hunter Dubbs
 * @version 9/21/2018
 * made for CIT360 at PCT
 * 
 * This class implements the Packet data type that is generated
 * and received by this program. It contains the Packet object's
 * data content represented by a String as well as the metadata:
 * from, to, size, and a timestamp.
 *
 */
public class Packet implements Comparable<Packet>{

	private String data, time;
	private int from, to, size;
	
	/**
	 * This is the full constructor for Packet. The size attribute
	 * is automatically calculated from the data argument.
	 * @param data the payload as a String
	 * @param from ip address of sender
	 * @param to ip address of recipient
	 * @param time timestamp when the packet was generated
	 */
	public Packet(String data, int from, int to, String time) {
		super();
		this.data = data;
		this.from = from;
		this.to = to;
		this.time = time;
		this.size = data.length();
	}

	/**
	 * This method returns the data content of Packet.
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * This method sets the data content of Packet.
	 * @param data the data to be set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * This method returns the IP address of the source.
	 * @return the source IP address
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * This method sets the IP address of the source.
	 * @param from the source IP address
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * This method returns the IP address of the destination.
	 * @return the destination IP address
	 */
	public int getTo() {
		return to;
	}

	/**
	 * This method sets the IP address of the destination.
	 * @param to the destination IP address
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/**
	 * This method returns the timestamp from Packet.
	 * @return the timestamp
	 */
	public String getTime() {
		return time;
	}

	/**
	 * This method sets the timestamp for Packet.
	 * @param time the timestamp
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * This method returns the size of the data content of Packet.
	 * @return the size of the data
	 */
	public int getSize() {
		return size;
	}

	/**
	 * This method sets the size of the data content of Packet.
	 * @param size the size of the data
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * This method returns the String representation of Packet.
	 * @return the String representation
	 */
	@Override
	public String toString() {
		return "Packet [data=" + data + ", from=" + from + ", to=" + to 
				+ ", time=" + time + ", size=" + size + "]";
	}

	/**
	 * This method returns 0 if the specified Packet is the same as
	 * this Packet, or 1 if it is not.
	 * @param p the Packet object to compare this Packet to
	 * @return whether or not the Packet is the same as this Packet
	 */
	@Override
	public int compareTo(Packet p) {
		if (data.equals(p.getData()) && time.equals(p.getTime()) && from == p.getFrom() 
				&& to == p.getTo() && size == p.getSize()) {
			return 0;
		}
		return 1;
	}

	
}
