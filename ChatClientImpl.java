package Project1;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient{
    String myusrname;

    public ChatClientImpl(String name) throws RemoteException{
        myusrname=name;
    }

    public void receiveMessage (String sender_name,String message) throws RemoteException{
        if(!sender_name.equals(myusrname)){
            System.out.println(sender_name + ": " + message);
             
            System.out.flush();

        }
        

    };
    
}
