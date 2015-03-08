package airline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DataBaseInterface;

public class Scheduler implements java.io.Serializable{
	
	private List<Flight> flights;
	private List<FlightPath> paths;
	private Hangar hangar;
	private FlightNumberGen flightNumberGen;

	
	public Scheduler(){
		flights = new ArrayList<Flight>();
		paths = new ArrayList<FlightPath>();
		hangar = new Hangar();
		flightNumberGen = new FlightNumberGen();
		loadFlightInfoTest();
	}
	
	private void loadFlightInfoTest(){
		//load the flights from the database
		
		/*test data!*/
		
		flights = new ArrayList<Flight>();
		paths = new ArrayList<FlightPath>();
		
		FlightPath p1 = new FlightPath("xxy", "xyz", 200);
		FlightPath p2 = new FlightPath("xyz", "xxy", 200);
		
		FlightPath p3 = new FlightPath("xxy", "zyz", 200);
		FlightPath p4 = new FlightPath("zyz", "xyz", 200);
		
		paths.add(p1);
		paths.add(p2);
		paths.add(p3);
		paths.add(p4);
		
		Plane plane1 = new Plane("xxy", "fastPlane" , 10, 10, 30, 0, 200);
		Plane plane2 = new Plane("xxy", "slowPlane" , 50, 10, 50, 1, 100);
		Plane plane3 = new Plane("xxy", "coolPlane" , 30, 30, 30, 2, 150);
		
		addPlane(plane1);
		addPlane(plane2);
		addPlane(plane3);
		
		
		Flight f1 = new Flight(plane1, p1, (new Date(2015 - 1900,0,1)), flightNumberGen.getNextFlightNumber());
		Flight f2 = new Flight(plane2, p2, (new Date(2015 - 1900,0,2)), flightNumberGen.getNextFlightNumber());
		Flight f3 = new Flight(plane2, p1, (new Date(2015 - 1900,0,3)), flightNumberGen.getNextFlightNumber());
		Flight f4 = new Flight(plane2, p2, (new Date(2015 - 1900,0,4)), flightNumberGen.getNextFlightNumber());
		Flight f5 = new Flight(plane2, p1, (new Date(2015 - 1900,0,5)), flightNumberGen.getNextFlightNumber());
		Flight f6 = new Flight(plane2, p2, (new Date(2015 - 1900,0,6)), flightNumberGen.getNextFlightNumber());
		Flight f7 = new Flight(plane2, p3, (new Date(2015 - 1900,0,7)), flightNumberGen.getNextFlightNumber());
		Flight f8 = new Flight(plane2, p4, (new Date(2015 - 1900,0,8)), flightNumberGen.getNextFlightNumber());

		
		addFlight(f7);
		addFlight(f2);
		addFlight(f4);
		addFlight(f6);
		addFlight(f3);
		addFlight(f1);
		addFlight(f5);
		addFlight(f8);
		
		
	}
	/*
	public Hangar getHangar(){
		return hangar;
		
	}
	*/
	
	public List<Flight> getFlightsPlane(Plane p){
		List<Flight> flightsPlane = new ArrayList<Flight>();
		for(int i=0;i<flights.size();i++){
			if(flights.get(i).getPlane().equals(p)){
				flightsPlane.add(flights.get(i));
			}
		}
		return flightsPlane;
		
		
		
	}
	
	public String getNextFlightNum() {
		return flightNumberGen.getNextFlightNumber();
	}
	
	public Flight getFlight(String flightNo){
		
		for(int i=0;i<flights.size();i++){
			if(flights.get(i).getFlightNumber().equalsIgnoreCase(flightNo)){
				return flights.get(i);
			}
		}
		return null;
		
	}
	
	/*
	public List<Flight> getFlightsBetweenDate(Date d1, Date d2){
		
		List<Flight> flightsDate = new ArrayList<Flight>();
		for(int i=0;i<flights.size();i++){
			if( (flights.get(i).getDate().after(d1)) && (flights.get(i).getDate().before(d2)) ){
				flightsDate.add(flights.get(i));
			}
		}
		return flightsDate;
		
	}
	*/
	public List<Flight> getFlightsPath(FlightPath path){
		List<Flight> flightsPath = new ArrayList<Flight>();
		for(int i=0;i<flights.size();i++){
			if(flights.get(i).getPath().equals(path)){
				flightsPath.add(flights.get(i));
			}
		}
		return flightsPath;
	}
	public List<Flight> getFlightsAll(){
		return flights;
	}
	
	public void addFlight(Flight f){
		if(!checkConflict(f))
			insertFlight(f);
		
		
	}
	
	public void addFlight(List<Flight> f){
		if(!checkConflict(f)){
			for(int i=0;i<f.size();i++)
				insertFlight(f.get(i));
		}
		
	}
	
	public boolean checkConflict(Flight f){
		//TODO
		
		for(int i=0;i<flights.size();i++){
			
		}
		return false;
		
	}
	public boolean checkConflict(List<Flight> f){
		//TODO
		
		return false;
		
	}
	/*
	public Flight getConfliction(List<Flight> f){
		return null;
	}
	public Flight getConfliction(Flight f){
		return null;
	}
	*/
	/**
	 * inserts the flight f into the appropriate spot
	 * sorted by date
	 * @param f
	 */
	private void insertFlight(Flight f){
		
		if(flights.size() == 0){
			flights.add(f);
			DataBaseInterface.SaveScheduler(this);
			return;
		}
		
		int size = flights.size();
		
		for(int i=0;i<size;i++){
			if((flights.get(i).getDate().after(f.getDate()))){
				flights.add(i,f);
				DataBaseInterface.SaveScheduler(this);
				return;
			}
			
		}
		flights.add(f);
		DataBaseInterface.SaveScheduler(this);
		
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i=0;i<flights.size();i++){
			s+= flights.get(i).toString() + "\n";
		}
		return s;
	}
	
	public List<FlightPath> getAllPaths(){
		return paths;
	}
	
	public boolean addNewFlightPath(FlightPath p){
		
		for(int i=0;i<paths.size();i++){
			if(paths.get(i).equals(p)){
				return false;
			}
		}
		
		paths.add(p);
		DataBaseInterface.SaveScheduler(this);
		return true;
		
		
	}
	
	public List<Plane>getAllPlanes(){
		return hangar.getAllPlanes();
	}
	/*
	public List<Plane> getPlanes(int MinSeats){
		//TODO get all planes that match the min seat requirment
		return hangar.getPlanes(MinSeats);
	}
	*/
	
	public boolean addPlane(Plane plane){
		boolean b = hangar.addPlane(plane);
		DataBaseInterface.SaveScheduler(this);
		return b;
		
	}
	public int numberOfPlanes(){
		return hangar.size();
	}
	public Plane getPlane(int idx){
		return hangar.getPlane(idx);
	}
	
	/*
	public static void main(String args[]){
		
		Scheduler s = new Scheduler();		
		System.out.println(s);
		System.out.println("************between date feb 3 - 7******************");
		
		List<Flight> p = s.getFlightsBetweenDate(new Date(2015,1,3), new Date(2015,1,7));
		
		for(int i = 0; i< p.size(); i++){
			System.out.println(p.get(i));
		}
		
		System.out.println("************plane slow plane******************");
		
		List<Flight> p1 = s.getFlightsPlane(hangar.getPlane(1));
		
		for(int i = 0; i< p1.size(); i++){
			System.out.println(p1.get(i));
		}
		System.out.println("************plane fast plane******************");
		
		List<Flight> p2 = s.getFlightsPlane(hangar.getPlane(0));
		
		for(int i = 0; i< p2.size(); i++){
			System.out.println(p2.get(i));
		}
		
		System.out.println("************add fast plane******************");
		s.addFlight(new Flight(hangar.getPlane(0), new FlightPath("xyz", "xxy", 200), new Date(), "n111"));
		
		List<Flight> p3 = s.getFlightsPlane(hangar.getPlane(0));
		
		for(int i = 0; i< p3.size(); i++){
			System.out.println(p3.get(i));
		}
		

	}
	*/

	

}
