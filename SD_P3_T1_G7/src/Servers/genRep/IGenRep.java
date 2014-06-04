package Servers.genRep;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Driver.IDriverGenRep;
import Passenger.IPassengerGenRep;
import Porter.IPorterGenRep;
import Servers.bus.IBusGenRep;

public interface IGenRep extends IPassengerGenRep, IDriverGenRep,
IPorterGenRep, IBusGenRep, Remote {

	int getNumFlights() throws RemoteException;

	int getNumPassengers() throws RemoteException;

	int getMaxBags() throws RemoteException;

	void waitForPorterToDie() throws RemoteException;

	void waitForDriverToDie() throws RemoteException;
	
	void endSimulation() throws RemoteException;

	int getNumBusSeats() throws RemoteException;
}
