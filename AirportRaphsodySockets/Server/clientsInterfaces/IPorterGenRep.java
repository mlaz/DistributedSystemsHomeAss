package Server.clientsInterfaces;
import Server.EPorterStates;
import Server.ServerInfo;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterGenRep {

	ServerInfo getArrivalTerminal();

	ServerInfo getBaggagePickupZone();

	ServerInfo getTempBaggageStorage();

	void registerPorter();

	void removeLuggageAtPlane();

	void incLuggageAtCB();

	void incLuggageAtSR();

	void updatePorterState(EPorterStates state);

}