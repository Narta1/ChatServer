import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.SwingUtilities;

public class ChatClientGUI extends UnicastRemoteObject implements ChatClient {
    
    private ClientGUI gui;
    
    public ChatClientGUI(ClientGUI gui) throws RemoteException {
        super();
        this.gui = gui;
    }
    
    @Override
    public void receiveMessage(String sender, String message) throws RemoteException {
        
        SwingUtilities.invokeLater(() -> {
            gui.displayMessage(sender + ": " + message);
        });
    }
}

