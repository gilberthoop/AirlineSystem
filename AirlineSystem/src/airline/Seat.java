package airline;


public abstract class Seat implements java.io.Serializable {
	
	public static enum SeatType { FistClass, Coach, Economy};
	public static int SEAT_FAIR = 10000; // 100.00 
	
	protected boolean isReserved = false;
	protected boolean isConfirmed= false;
	protected String seatID;
	protected Ticket ticket;
	protected float discount;
	protected Flight flight;
	protected Passenger pReserved;
	
	public Seat(String seatID, float d, Flight f){
		this.flight = f;
		this.seatID = seatID;
		this.discount = d;
	}
	public int getFair(){
		return (int) (SEAT_FAIR*(discount));
	}
	
	/**
	 * @return the amount to be refunded and sets the seat to available
	 * 
	 * this sets the ticket to null
	 * 
	 */
	public int refund(){
		isReserved = false;
		isConfirmed = false;
		ticket = null;
		return getFair();		
	}
	public String getSeatNumber(){
		return seatID;
	}
	
	public void reserve(Passenger p){
		pReserved = p;
		isReserved = true;
	}
	public Passenger getReservePassenger(){
		return pReserved;
	}
	public Ticket getTicket(){
		return ticket;
	}
	
	public Ticket confirm(Passenger p){
		isConfirmed = true;
		isReserved = true;
		pReserved = p;
		ticket = new Ticket(flight, this, p);
		return ticket;
	}
	
	public boolean isConfirmed(){
		return isConfirmed;
	}
	public boolean isReserved(){
		return isReserved;
	}
	public void removeReservation() {
		isReserved = false;
		pReserved = null;
		
		
	}
	
	@Override
	public String toString(){
		String s = seatID;
		if(isConfirmed)
			s+= " taken";
		else if(isReserved)
			s+= " reserved";
		else s+= " available";
		return s;
		
	}
	
	public abstract SeatType getType();
	public abstract boolean isRefundable();
	
	

}
