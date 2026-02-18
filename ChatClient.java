package Project1;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface ChatClient extends Remote {

    public void receiveMessage (String sender_name,String message) throws RemoteException;
    
}
