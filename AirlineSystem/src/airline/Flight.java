package airline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Flight implements java.io.Serializable{
	
	private List<Seat> seats;
	private Plane plane;
	private FlightPath path;
	private Date date;
	private String flightNo;
	
	public Flight(Plane plane, FlightPath path, Date date,String flightNo){
		this.plane = plane;
		this.date  = date;
		this.path  = path;
		this.flightNo = flightNo;
		
		createSeats();
	}
	
	private void createSeats(){
		seats = new ArrayList<Seat>();
		
		for(int i=0; i<plane.getCoachSeats();i++){
			seats.add(new CoachSeat(flightNo+"-" +"C"+i,this));
			
		}
		
		for(int i=0; i<plane.getEconomySeats();i++){
			seats.add(new EconomySeat(flightNo+"-" +"E"+i,this));
			
		}
		
		for(int i=0; i<plane.getFirstClassSeats();i++){
			seats.add(new FirstClassSeat(flightNo+"-" +"F"+i,this));
			
		}
	}
	
	public Plane getPlane(){
		return plane;
	}
	public Date getDate(){
		return date;
	}
	public FlightPath getPath(){
		return path;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	public String getFlightNumber(){
		return flightNo;
	}
	public Seat getSeat(String seatNo){
		for(int i=0;i<seats.size();i++){
			if(seats.get(i).getSeatNumber().equalsIgnoreCase(seatNo))
				return seats.get(i);
		}
		return null;
	}
	
	@Override
	public String toString(){
		String s = flightNo + " - " + plane.getPlaneModel()+"-"+plane.getPlaneID()+ " - "+ date + " - " + path.getSource() + " - " + path.getDestination();
		return s;
	}
	
	/*
	public static void main(String args[]){
	
		
		FlightPath p = new FlightPath("xxy", "xyz", 200);
		FlightPath p2 = new FlightPath("xyz", "xxy", 200);
		
		Plane plane = new Plane("xxy", "fastPlane" , 100, 100, 100, 0, 200);
		
		Flight f = new Flight(plane, p, new Date(), "k111");
		Flight f2 = new Flight(plane, p2, new Date(), "k112");
		
		System.out.println(f);
		System.out.println(f2);
		
		
		
		
	}
	*/


}
