package airline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DataBaseInterface;

public class Booking {
	
	private List<Ticket> tickets;
	private List<Seat> reservedSeats;
	private Scheduler scheduler;
		
	public Booking(Scheduler scheduler){
		this.scheduler = scheduler;	
		loadBookingInfo();
	}
	
	private  void loadBookingInfo(){
		
		//load the info from the scheduler
		tickets = new ArrayList<Ticket>();
		reservedSeats = new ArrayList<Seat>();
		
		List<Flight> flight = scheduler.getFlightsAll();
		for(int i=0;i<flight.size();i++){
			List<Seat> seats = flight.get(i).getSeats();
			for(int j=0;j<seats.size();j++){
				if(seats.get(j).isConfirmed())
					tickets.add(seats.get(j).getTicket());
				if(seats.get(j).isReserved())
					reservedSeats.add(seats.get(j));
			}		
		}	
	}
	
	private void saveBookingInfo(){
		DataBaseInterface.SaveScheduler(scheduler);
	}
	public List<Ticket> getAllTickets(){
		return tickets;
	}
	
	public List<Ticket> getTickets(Passenger p){
		loadBookingInfo();	
		List<Ticket> pTickets = new ArrayList<Ticket>();
		for(int i=0;i<tickets.size();i++){
			if(tickets.get(i).getPassenger().equals(p))
				pTickets.add(tickets.get(i));
		}
		
		return pTickets;
	}
	
	public int refundTicket(Ticket ticket){
		int amount;
		tickets.remove(ticket);
		
		//amount = ticket.getSeat().refund();
		amount = getSeat(ticket.getSeatID()).refund();
		
		loadBookingInfo();
		saveBookingInfo();
		return amount;
		
	}
	public int getRefundAmount(Ticket ticket){
	
		if(isRefundable(ticket))
			return getSeat(ticket.getSeatID()).getFair();
		else return 0;
	}
	
	public int getTicketPrice(Ticket ticket){
		
		if(isRefundable(ticket))
			return getSeat(ticket.getSeatID()).getFair();
		else return 0;
	}
	
	public boolean isRefundable(Ticket ticket){
		return(getSeat(ticket.getSeatID()).isRefundable());
	}
	
	public Ticket bookSeat(String flightNo, String seatNo, Passenger p){
		Seat seat = scheduler.getFlight(flightNo).getSeat(seatNo);
		Ticket t = seat.confirm(p);
		tickets.add(t);
		
		loadBookingInfo();
		saveBookingInfo();
		
		
		return t;
	}
	public void reserveSeat(String flightNo, String seatNo,Passenger p){
		Seat seat = scheduler.getFlight(flightNo).getSeat(seatNo);
		seat.reserve(p);
		
		loadBookingInfo();
		saveBookingInfo();
		
	}

	public Ticket getTicket(String ticketID) {
		for(int i=0;i<tickets.size();i++)
			if(tickets.get(i).getTicketID().equalsIgnoreCase((ticketID)))
				return tickets.get(i);
		System.out.println("ticketID: "+ticketID+ " not found");
		return null;
	}
	
	public List<Seat> getReservedSeat(Passenger p){
		List<Seat> pSeats = new ArrayList<Seat>();
		
		for(int i = 0;i<reservedSeats.size();i++){
			if(reservedSeats.get(i).getReservePassenger().equals(p))
				pSeats.add(reservedSeats.get(i));
		}
		return pSeats;	
	}
	
	public void cancelReservation(String seatID){
		loadBookingInfo();
		
		for(int i=0;i<reservedSeats.size();i++){
			if(reservedSeats.get(i).getSeatNumber().equalsIgnoreCase(seatID)){
				reservedSeats.get(i).removeReservation();
				reservedSeats.remove(i);
				break;
			}
				
		}
		loadBookingInfo();
		saveBookingInfo();
	}

	public Seat getSeat(String seatID) {
	
		List<Flight> flight = scheduler.getFlightsAll();
		for(int i=0;i<flight.size();i++){
			List<Seat> seats = flight.get(i).getSeats();
			for(int j=0;j<seats.size();j++){
				if(seats.get(j).getSeatNumber().equalsIgnoreCase(seatID))
					return seats.get(j);
			}
		}
				
		return null;
	}
			
}
