package airline;

import java.util.List;

public class FlightPath implements java.io.Serializable{
	
	private String source;
	private String destination;
	private int distance;
	
	public FlightPath(String source, String destination,int distance){
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}
	
	public String getSource(){
		return source;
	}
	public String getDestination(){
		return destination;
	}
	public int getDistance(){
		return distance;
	}
	public String toString(){
		return source+" to "+ destination;
	}
	/**
	 * returns true if the source of the parameter matches the destination
	 * @param flightpath
	 * @return true if the source of the parameter matches the destination
	 */
	public boolean isConnecting(FlightPath p){
		return (this.destination.equalsIgnoreCase((p.source)));
	}
	public boolean equals(FlightPath p){
		return (p.source.equalsIgnoreCase(this.source) && p.destination.equalsIgnoreCase(this.destination));
	}

	
}
