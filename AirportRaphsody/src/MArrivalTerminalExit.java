import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MArrivalTerminalExit implements IDriverArrivalTerminalTransferZone, IPassengerArrivalExitTransferZone {

	private int totalPassengers;
	private int passengersDone = 0;
	private int nSeats;
	private int passengersToGo;
	private Queue<Integer> busQueue;
	private boolean availableBus = false;
	
	
	/**
	 * @param t
	 * @param genRep
	 */
	public MArrivalTerminalExit(int nAirplanes, int nPassengers, int nSeats, MGeneralRepository genRep) {
		genRep.setArrivalTerminalExit(this);
		this.nSeats = nSeats;
		busQueue = new LinkedList<Integer>();
		totalPassengers = nAirplanes * nPassengers;
	}

	/* (non-Javadoc)
	 * @see IPassengerArrivalTerminalTransferZone#takeABus()
	 */
	@Override
	public synchronized void takeABus(int passNumber) throws InterruptedException {
		busQueue.add((Integer)passNumber);
		if (busQueue.size() == nSeats)
			notifyAll();
		
		System.out.println("Passenger " + passNumber + " Qsize" + busQueue.size());
		while ( (passNumber != busQueue.peek()) || (passengersToGo == 0) || (!availableBus) )
			wait();
		
		passengersToGo--;
		passengersDone++;
		busQueue.poll();
		if (passengersToGo == 0)
			availableBus = false;
		notifyAll();
	}

	/* (non-Javadoc)
	 * @see IDriverArrivalTerminalTransferZone#announcingBusBoaring()
	 */
	@Override
	public synchronized boolean announcingBusBoaring() throws InterruptedException {
		if (totalPassengers == passengersDone)
			return false;// the simulation is over there are no more passengers to process
		
		
		passengersToGo = nSeats;
		availableBus = true;
		if (busQueue.isEmpty())
			wait();
		else
			notifyAll();
		System.out.println("DRIVER ANNOUNCED");
		return true;
	}
	
	/**
	 * 
	 */
	public synchronized void goHome() {
		// TODO Auto-generated method stub
		passengersDone++;
	}

	@Override
	public synchronized void announcingDeparture() {
		availableBus = false;
	}

}
