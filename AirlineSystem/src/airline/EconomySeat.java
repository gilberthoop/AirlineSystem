package airline;

import java.util.Date;

public class EconomySeat extends Seat {
	
	private static float economyDiscount = 0.7f;
	
	private long REFUND_TIME = 86400000 * 14;// 14 days in milliseconds

	public EconomySeat(String seatID, Flight f) {
		super(seatID,economyDiscount, f);
		
	}

	@Override
	public SeatType getType() {
		return SeatType.Economy;
	}
	
	@Override
	public boolean isRefundable(){
		Date today = new Date();
		Date minRefundDate = new Date(flight.getDate().getTime() - REFUND_TIME);
		if(today.after(minRefundDate))
			return false;
		else return true;
	}
	
	
	@Override
	public int refund(){
		if(!isRefundable())
			return 0;
		
		else{
			isReserved = false;
			isConfirmed = false;
			ticket = null;
			return getFair();	
		}
		
		
	}

}
