package airline;

public class Passenger implements java.io.Serializable{
	
	private String name;
	private String userID;
	
	public Passenger(String name, String userID){
		this.name = name;
		this.userID = userID;
	}
	
	public String getName(){
		return name;
	}

	public String getID(){
		return userID;
	}
	
	public boolean equals(Passenger p){
		return ((this.name.equalsIgnoreCase(p.getName())) && (this.userID.equalsIgnoreCase(p.getID())));
	}
	
	public String toString(){
		return name +", "+userID;
	}
}
