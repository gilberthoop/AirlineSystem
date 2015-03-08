package airline;



public class FirstClassSeat extends Seat{
	private static float firstClassDiscount = 1f;
	
	public FirstClassSeat(String seatID,Flight f){
		super(seatID,firstClassDiscount,f);
		
	}
	
	@Override
	public SeatType getType() {
		return SeatType.FistClass;
	}

	@Override
	public boolean isRefundable() {
		return true;
	}



	
}
