package Utils;

public class VectorClock implements Comparable {
	private int[] clock;
	
	public VectorClock(int size) {
		this.clock = new int[size];
	}
	
	public void increment(int id) throws ArrayIndexOutOfBoundsException {
		clock[id]++;
	}
	
	public int[] getClock() {
		return clock;
	}
	
	public int getClockSize() {
		return clock.length;
	}
	
	public void updateClock(VectorClock external) {
		if( this.getClockSize() != external.getClockSize() )
			throw new IllegalArgumentException();
		
		int[] ext = external.getClock();
		for(int i=0 ; i<this.getClockSize() ; i++ ) {
			if( clock[i] < ext[i]) {
				clock[i] = ext[i];
			}
		}
	}

	@Override
	public int compareTo(Object obj) {
		VectorClock other = (VectorClock) obj;
		
		int sumThis  = sum( this.getClock() );
		int sumOther = sum( other.getClock() );
		
		return sumThis - sumOther;
	}
	
	private int sum(int[] array) {
		int sum = 0;
		for(int i:array) {
			sum+=i;
		}
		return sum;
	}
}