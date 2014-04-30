package Passenger;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class CommPassBaggageReclaimGuichet implements IPassengerBaggageReclaimGuichet {
	private ServerInfo reclaimGuichetInfo;
	
	private String myDebugName = "PASS_RECLAIM";
	
	public CommPassBaggageReclaimGuichet( ServerInfo reclaimGuichetInfo ) {
		this.reclaimGuichetInfo = reclaimGuichetInfo;
	}
	@Override
	public void reclaimBags(int passengerNumber) {
		ClientCom con = new ClientCom(reclaimGuichetInfo.getHostName(), reclaimGuichetInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.RECLAIM_BAGS, passengerNumber);
		
		printMessageSummary(outMessage, con, reclaimGuichetInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();
		
		printMessageSummary(inMessage, con, reclaimGuichetInfo, false);

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	private void printMessageSummary(Message m, ClientCom con, ServerInfo id, boolean outMessage) {
		if( outMessage ) {
			System.out.println(myDebugName+" ("+con.commSocket.getLocalPort()+") sending message to " + id.getHostName() + ":"+id.getPortNumber());
		} else {
			System.out.println(myDebugName+" ("+con.commSocket.getLocalPort()+") received message from " + id.getHostName() + ":"+id.getPortNumber());
		}
		m.print();
	}
}
