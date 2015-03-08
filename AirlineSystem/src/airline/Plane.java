package airline;
import java.util.List;


public class Plane implements java.io.Serializable{
	
	private String location;
	private String planeModel;
	private long planeID;
	private int firstClassSeats,coachSeats,economySeats;
	private float airSpeed;
	//private int currentFlyTime;
	//private long totalFlyTime;
	
	public Plane(String location, String planeModel, int coachSeats, int firstClassSeats, int economySeats, long planeID, float airSpeed){
		this.location	 	= location;
		this.planeModel	 	= planeModel;
		this.planeID 	 	= planeID;
		this.firstClassSeats = firstClassSeats;
		this.economySeats 	= economySeats;
		this.coachSeats 	= coachSeats;
		this.airSpeed		= airSpeed;
		//currentFlyTime = 0;
		//totalFlyTime   = 0;
	}
	/**
	 * 
	 * @param location, the destination airport
	 * @param seconds, to eliminate rounding errors.
	 */
	public void fly(String location, int seconds){
		this.location 	= location;
		//currentFlyTime 		+= seconds;
		//totalFlyTime	+= seconds;
	}
	public float getAirSpeed(){
		return airSpeed;
	}
	public String getLocation(){
		return location;
	}
	public long getPlaneID(){
		return planeID;
	}
	public String getPlaneModel(){
		return planeModel;
	}
	public int getFirstClassSeats(){
		return firstClassSeats;
	}
	public int getEconomySeats(){
		return economySeats;
	}
	public int getCoachSeats(){
		return coachSeats;
	}
	public int getTotalSeats(){
		return coachSeats+economySeats+firstClassSeats;
	}
	public boolean equals(Plane p){
		return ((p.getPlaneID() == this.planeID));
	}
	@Override
	public String toString(){
		return planeModel + "-" + planeID;
	}
	/*
	public void maintenance(){
		currentFlyTime = 0;
	}
	
	public int getFlyTimeSinceLastMaintenance(){
		return currentFlyTime;
	}
	public long getTotalFlyTime(){
		return totalFlyTime;
	}
	*/
	
}
