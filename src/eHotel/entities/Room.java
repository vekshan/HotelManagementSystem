package eHotel.entities;

public class Room {
	
	private String h_name;
	private int room_no;
	private int capacity;
	private double price;

	
	public Room() {
		
	}


	public Room(String h_name, int room_no, int capacity, double price) {
		super();
		this.h_name = h_name;
		this.room_no = room_no;
		this.capacity = capacity;
		this.price = price;
	}


	public int getCapacity() {
		return capacity;
	}




	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public String getH_name() {
		return h_name;
	}


	public void setH_name(String h_name) {
		this.h_name = h_name;
	}


	public int getRoom_no() {
		return room_no;
	}


	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
