package Servers.baggagePickupZone;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerBaggagePickupZone {
	private static int portNumber = 22164;
	private static String usage = "Usage: java ServerArrivalTerminal [RMIRegName] [RMIRegPort]";

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
    	if (args.length != 1) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[1];
			args[0] = "localhost";
		}
    	/* obter parametros do problema */
		
		Registry genRepRegistry = null;
		try {
			genRepRegistry = LocateRegistry.getRegistry(args[0], RmiUtils.rmiPort);
		} catch( RemoteException e ) {
			System.err.println( "Error accessing the RMI registry: " + e.getMessage() );
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println( "GenRep RMI registry accessed" );
		
		int numEntities = 0;
		IGenRep genRep = null;
		try {
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			numEntities = genRep.getNumPassengers() + 2;
		} catch (AccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* establecer o serviço */
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone(numEntities);
		IBaggagePickupZone baggagePickupInter = null;
		
		try {
			baggagePickupInter = (IBaggagePickupZone) UnicastRemoteObject.exportObject(baggagePickupZone, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the TempStorage stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Baggage Pickup Zone stub created");
		
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(RmiUtils.rmiPort);
		} catch( RemoteException e ) {
			System.err.println( "Error accessing the RMI registry: " + e.getMessage() );
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println( "Local RMI registry accessed" );
		
		try {
			registry.bind(RmiUtils.baggagePickupZoneId, baggagePickupInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the BaggagePickupZone to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Baggage Pickup Zone binded to RMI registry (port " + portNumber + ")");
		 try {
				genRep.registerService(RmiUtils.baggagePickupZoneId, InetAddress.getLocalHost().getHostName(), portNumber);
			} catch (RemoteException | UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Ready");	
	}
}