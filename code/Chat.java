import java.rmi.RemoteException;
import java.util.List;
import java.rmi.Remote;
import java.util.Scanner;

public interface Chat extends Remote {

    public void join (String name,ChatClient client) throws RemoteException;
    public void leave(String name) throws RemoteException;
    public void write (String name,String message) throws RemoteException;
    public List<String> read (String name) throws RemoteException;
    public void save_Chat() throws RemoteException;
    public void load_Chat() throws RemoteException;

    
}
