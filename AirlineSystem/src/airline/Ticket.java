package airline;

public class Ticket implements java.io.Serializable{
	
	private Flight flight;
	private String seatId;
	private Passenger passenger;
	private String ticketID;
	
	public Ticket(Flight f, Seat s, Passenger p){
		this.flight = f;
		this.seatId = s.getSeatNumber();
		this.passenger = p;
		
		this.ticketID = "t" + s.getSeatNumber();
		
	}
	
	public Flight getFlight(){
		return flight;
	}
	
	/*
	public Seat getSeat(){
		return seat;
	}
	*/
	
	public Passenger getPassenger(){
		return passenger;
	}
	
	public String getSeatID(){
		//return flight.getFlightNumber() + "-" +seat.getSeatNumber();
		//return seat.getSeatNumber();
		return seatId;
	}
	
	@Override
	public String toString(){
		return ticketID + " flight: "+flight.getFlightNumber()+" from: "+flight.getPath().getSource()+
				" to : " + flight.getPath().getDestination();
	}

	public String getTicketID() {
		
		return ticketID;
	}
	
	@Override
	public boolean equals(Object t){
		return this.ticketID.contentEquals( ((Ticket) t).getTicketID());
	}

}
