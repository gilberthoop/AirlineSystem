package airline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DataBaseInterface;

import airline.Seat.SeatType;

public class AirlineSystem{
	
	Scheduler scheduler;
	Booking booking;
	
	public AirlineSystem(){
		scheduler = DataBaseInterface.LoadScheduler();
		booking   = new Booking(scheduler);
		
	}
	/*
	public void loadBooking(){
		//booking = new Booking(scheduler);
	}
	*/
	public void saveToDataBase(){
		DataBaseInterface.SaveScheduler(scheduler);
	}
	
	//****************Employee scheduling****************************
	public Flight createFlightObject(Plane tPlane, FlightPath tPath, Date tDate) {
		return new Flight(tPlane,tPath,tDate,scheduler.getNextFlightNum());
	}
	
	public List<Plane> getAllPlanes(){
		return scheduler.getAllPlanes();
	}
	
	public boolean addPlane(Plane plane){
		if(isValidPlane(plane)){
			return scheduler.addPlane(plane);
		}
		return false;
	}
	
	public boolean isValidPlane(Plane plane){
		return true;
	}
	
	public List<FlightPath> getALlPaths(){
		return  scheduler.getAllPaths();
		
	}
	
	public List<Flight> getAllFlights(){
		return scheduler.getFlightsAll();
	}
	
	/**
	 * searches the flights for a flightNo or dest/source containing the string
	 * 
	 * @param the search term
	 * @return
	 */
	/*
	public List<Flight> searchFlight(String search){
		List<Flight> flightMatches = new ArrayList<Flight>();
		List<Flight> flights = scheduler.getFlightsAll();
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().contains(search) || 
					flights.get(i).getPath().getDestination().contains(search) ||
					flights.get(i).getPath().getSource().contains(search)){
				flightMatches.add(flights.get(i));
			}
		return flightMatches;
	}
	*/
	
	
	public List<Flight> getFlights(Plane plane){
		return scheduler.getFlightsPlane(plane);
	}
	
	public List<FlightPath> getAllFlightPaths(){
		return scheduler.getAllPaths();
	}
	public List<Flight> getFlights(FlightPath path){
		return scheduler.getFlightsPath(path);
	}
	
	public void addFlight(Flight flight){
		
		scheduler.addFlight(flight);
	}
	
	public void addFlight(List<Flight> f){
		scheduler.addFlight(f);
	}
	
	public boolean checkConflict(Flight f){
		return scheduler.checkConflict(f);
		
	}
	public boolean checkConflict(List<Flight> f){
		return scheduler.checkConflict(f);
		
	}
	
	//****************Customer booking****************************
	/**
	 * 
	 * @param flightNo
	 * @return a list of seat numbers on the particular flight
	 */
	public List<String> getAllSeats(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						seatNo.add(seats.get(i).getSeatNumber());
					}
					break;
			}
		
		return seatNo;
	}
	/**
	 * 
	 * @param flightNo
	 * @return a list of FirstClass seats  numbers on the particular flight
	 */
	public List<String> getFirstClassSeats(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(i).getType() == SeatType.FistClass)
							seatNo.add(seats.get(i).getSeatNumber());
					}
					break;
			}
		
		return seatNo;
	}
	
	/**
	 * 
	 * @param flightNo
	 * @return a list of FirstClass seats  numbers on the particular flight
	 */
	public List<String> getCoachSeats(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(i).getType() == SeatType.Coach)
							seatNo.add(seats.get(i).getSeatNumber());
					}
					break;
			}
		
		return seatNo;
	}
	/**
	 * 
	 * @param flightNo
	 * @return the SeatIDs
	 */
	public List<String> getEconomySeats(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(i).getType() == SeatType.Economy)
							seatNo.add(seats.get(j).getSeatNumber());
					}
					break;
			}
		
		return seatNo;
	}
	/*
	public String getNextAvailableEconomySeat(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		String seatNo = "";
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Economy && !seats.get(j).isConfirmed() && !seats.get(j).isReserved())
							seatNo = (seats.get(j).getSeatNumber());
						}
					
					break;
					
			}
		
		return seatNo;
	}
	*/
	/*
	public String getNextAvailableBookingEconomySeat(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		String seatNo = "";
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Economy && !seats.get(j).isConfirmed() && !seats.get(j).isReserved())
							seatNo = (seats.get(j).getSeatNumber());
						}
					
					break;
					
			}
		if(seatNo == ""){//hasent found one, now check reserved seats
			for(int i=0;i<flights.size();i++)
				if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
						seats = flights.get(i).getSeats();
						for(int j =0; j<seats.size();j++){
							if(seats.get(j).getType() == SeatType.Economy && !seats.get(j).isConfirmed() && seats.get(j).isReserved())
								seatNo = (seats.get(j).getSeatNumber());
							}
						
						break;
						
				}
			
		}
		
		
		return seatNo;
	}
	
	public String getNextAvailableBookingFirstClassSeat(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		String seatNo = "";
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.FistClass && !seats.get(j).isConfirmed() && !seats.get(j).isReserved())
							seatNo = (seats.get(j).getSeatNumber());
						}
					
					break;
					
			}
		if(seatNo == ""){//hasent found one, now check reserved seats
			for(int i=0;i<flights.size();i++)
				if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
						seats = flights.get(i).getSeats();
						for(int j =0; j<seats.size();j++){
							if(seats.get(j).getType() == SeatType.FistClass && !seats.get(j).isConfirmed() && seats.get(j).isReserved())
								seatNo = (seats.get(j).getSeatNumber());
							}
						
						break;
						
				}
			
		}
		
		
		return seatNo;
	}
	
	public String getNextAvailableBookingCoachSeat(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		String seatNo = "";
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Coach && !seats.get(j).isConfirmed() && !seats.get(j).isReserved())
							seatNo = (seats.get(j).getSeatNumber());
						}
					
					break;
					
			}
		if(seatNo == ""){//hasent found one, now check reserved seats
			for(int i=0;i<flights.size();i++)
				if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
						seats = flights.get(i).getSeats();
						for(int j =0; j<seats.size();j++){
							if(seats.get(j).getType() == SeatType.Coach && !seats.get(j).isConfirmed() && seats.get(j).isReserved())
								seatNo = (seats.get(j).getSeatNumber());
							}
						
						break;
						
				}
			
		}
		
		
		return seatNo;
	}
	
	public String getNextAvailableFirstClassSeat(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		String seatNo = "";
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.FistClass && !seats.get(j).isConfirmed() && !seats.get(j).isReserved())
							seatNo = (seats.get(j).getSeatNumber());
					}
					break;
			}

		return seatNo;
	}
	
	public String getNextAvailableCoachSeat(String flightNo){
		List<Flight> flights = scheduler.getFlightsAll();
		String seatNo = "";
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Coach && !seats.get(j).isConfirmed() && !seats.get(j).isReserved())
							seatNo = (seats.get(j).getSeatNumber());
					}
					break;
			}
		
		return seatNo;
	}
	*/
	public List<String> getNextAvailableCoachSeats(String flightNo,int number){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Coach && !seats.get(j).isConfirmed() && !seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		return seatNo;
	}
	
	
	public List<String> getNextAvailableCoachBookSeats(String flightNo,int number){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Coach && !seats.get(j).isConfirmed() && !seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Coach && !seats.get(j).isConfirmed() && seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		return seatNo;
	}
	
	public List<String> getNextAvailableEconomyBookSeats(String flightNo,int number){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Economy && !seats.get(j).isConfirmed() && !seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Economy && !seats.get(j).isConfirmed() && seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		return seatNo;
	}
	
	public List<String> getNextAvailableFirstClassBookSeats(String flightNo,int number){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.FistClass && !seats.get(j).isConfirmed() && !seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.FistClass && !seats.get(j).isConfirmed() && seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		return seatNo;
	}
	
	public List<String> getNextAvailableEconomySeats(String flightNo,int number){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.Economy && !seats.get(j).isConfirmed() && !seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		return seatNo;
	}
	public List<String> getNextAvailableFirstClassSeats(String flightNo,int number){
		List<Flight> flights = scheduler.getFlightsAll();
		List<String> seatNo = new ArrayList<String>();
		List<Seat> seats;
		
		for(int i=0;i<flights.size();i++)
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
					seats = flights.get(i).getSeats();
					for(int j =0; j<seats.size();j++){
						if(seats.get(j).getType() == SeatType.FistClass && !seats.get(j).isConfirmed() && !seats.get(j).isReserved() && number > 0){
							seatNo.add(seats.get(j).getSeatNumber());
							number--;
						}
					}
					break;
			}
		
		return seatNo;
	}
	
	/**
	 * 
	 * @param seatID
	 * @return a Ticket id
	 */
	public String bookSeat(String seatID,Passenger p){
		String[] seat = seatID.split("-");
		return booking.bookSeat(seat[0], seatID ,p).getTicketID();
		
	}
	
	/**
	 * 
	 * @param seatID
	 * @return a Ticket id
	 */
	public List<String> bookSeats(List<String> seatID,Passenger p){
		List<String> ticketID = new ArrayList<String>();
		for(int i=0;i<seatID.size();i++)
			ticketID.add(bookSeat(seatID.get(i),p));
		
		return ticketID;
		
	}
	
	/**
	 * this function should prob not be used
	 * @param p
	 * @return the total price of all the customers tickets
	 */
	/*
	public int getPassengerTotal(Passenger p){
		//TODO
		//this function should not be used.
		int total = 0;
		return total;
	}
	*/
	/*
	
	public int getTicketRefundPrice(String ticketID){
		Ticket t = booking.getTicket(ticketID);
		int total = 0;
		
		if(t != null)
			total = booking.getRefundAmount(t);
		
		return total;
	}
	*/
	public int getTicketPrice(String ticketID){
		Ticket t = booking.getTicket(ticketID);
		int total = 0;
		
		if(t != null)
			total = booking.getTicketPrice(t);
		
		return total;
	}
	
	public int getSeatPrice(String seatID){
		String[] seat = seatID.split("-");
		Seat s = booking.getSeat(seatID);
		if(s != null)
			return s.getFair();
		else return 0;
	}
	
	public void reserveSeat(String seatID,Passenger p){
		String[] seat = seatID.split("-");
		booking.reserveSeat(seat[0], seatID , p);
	}
	
	public void reserveSeat(List<String> seatID,Passenger p){
		
		for(int i=0;i<seatID.size();i++)
			reserveSeat(seatID.get(i),p);
		
	}
	
	/**
	 * 
	 * @param p
	 * @return a list of reserved seats for the passenger
	 */
	public List<String> getPassangerReservedSeats(Passenger p){
		List<Seat> seats = booking.getReservedSeat(p);
		List<String> seatsID = new ArrayList<String>();
		
		for(int i=0;i<seats.size();i++){
			if(!seats.get(i).isConfirmed)
				seatsID.add(seats.get(i).getSeatNumber());
		}
		return seatsID;
	}
	
	/**
	 * 
	 * @param p
	 * @return a list of ticket ids
	 */
	public List<String> getTickets(Passenger p){
		List<Ticket> tickets = booking.getTickets(p);
		List<String> tString = new ArrayList<String>();
		
		for(int i =0; i< tickets.size();i++){
			tString.add(tickets.get(i).getTicketID());
		}
		
		return tString;
	}
	
	public void cancelReservation(String seatID){
		booking.cancelReservation(seatID);
		
	}
	
	public boolean isRefundable(String ticketID){
		List<Ticket> tickets = booking.getAllTickets();
		Ticket ticket;
		for(int i=0;i<tickets.size();i++){
			if(tickets.get(i).getTicketID().equalsIgnoreCase(ticketID)){
				ticket = tickets.get(i);
				return booking.isRefundable(ticket);
			}
		}
		System.out.println("ticket not found");
		return false;
	}
	
	public int refundTicketPrice(String ticketID){
		List<Ticket> tickets = booking.getAllTickets();
		Ticket ticket;
		for(int i=0;i<tickets.size();i++){
			if(tickets.get(i).getTicketID().equalsIgnoreCase(ticketID)){
				ticket = tickets.get(i);
				int value = booking.getRefundAmount(ticket);
				return value;
			}
		}
		
		System.out.println("ticket not found");
		return 0;
	}
	
	public int refundTicket(String ticketID){
		List<Ticket> tickets = booking.getAllTickets();
		//System.out.println(tickets);
		Ticket ticket;
		for(int i=0;i<tickets.size();i++){
			if(tickets.get(i).getTicketID().equalsIgnoreCase(ticketID)){
				ticket = tickets.get(i);
				int value = booking.getRefundAmount(ticket);
				booking.refundTicket(ticket);
				return value;
			}
		}
		System.out.println("ticket not found");
		return 0;
	}
	

	/*
	public FlightPath getFlightPath(String source, String destination){
		//FlightPath; 
		return null;
	}
	*/
	
	public boolean addNewFlightPath(FlightPath p){
		return scheduler.addNewFlightPath(p);
	}
	
	
	
	
	

}
