package Server.departureTerminalEntrance;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class DepartureTerminalEntranceGenRepComm implements IDepartureTerminalEntranceGenRep {

	private ServerInfo genRepInfo;
	private ServerInfo departureTermEntranceInfo;
	
	public DepartureTerminalEntranceGenRepComm(ServerInfo genRepInfo, ServerInfo departureTermEntranceInfo) {
		this.genRepInfo = genRepInfo;
		this.departureTermEntranceInfo = departureTermEntranceInfo;
	}

	@Override
	public void setDepartureTerminalEntrance() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_DEPARTURE_TERMINAL_ENTRANCE, departureTermEntranceInfo.getPortNumber(), departureTermEntranceInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}