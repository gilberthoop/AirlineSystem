package airline;

public class CoachSeat extends Seat {
	
	private static float coachDiscount = 0.85f;

	public CoachSeat(String seatID,Flight f) {
		super(seatID, coachDiscount,f);
		
	}

	@Override
	public SeatType getType() {
		return SeatType.Coach;
	}

	@Override
	public boolean isRefundable() {
		
		return true;
	}

}
