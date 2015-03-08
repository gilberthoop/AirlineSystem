package airline;

public class FlightNumberGen implements java.io.Serializable {
	
	private String letter = "F";
	private int number = 200;
	
	public String getNextFlightNumber() {
		return letter + (number++);
	}

}
