
public class Packet{

	private Object data;
	private int from;
	private int to;
	private String time;
	private int size;
	
	public Packet(Object data, int from, int to, String time, int size) {
		super();
		this.data = data;
		this.from = from;
		this.to = to;
		this.time = time;
		this.size = size;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
