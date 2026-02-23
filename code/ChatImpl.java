import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatImpl extends UnicastRemoteObject implements Chat {

    private HashMap<String,ChatClient> participants;
    private List<String> history;
    private final Lock lock = new ReentrantLock();

    public ChatImpl () throws RemoteException{
        participants=new HashMap<>();
        history=new ArrayList<>();
        load_Chat();
    }   
    public synchronized void join (String name,ChatClient client) throws RemoteException{
        participants.put(name,client);
        System.out.println(name + " joined the chat.");
        
         
    };
    public synchronized void leave(String name) throws RemoteException{
        participants.remove(name);
        System.out.println(name + " left the chat.");
    };
    public synchronized void write (String name,String message) throws RemoteException{
        String fullMessage = name + ": " + message;
    
    
        history.add(fullMessage);
        for (Map.Entry<String,ChatClient> entry : participants.entrySet()){
            try {
                entry.getValue().receiveMessage(name, message);
            } catch (Exception e) {
                System.err.println("Failed to send to " + entry.getKey());
            }
            

        }
    };
    

    public synchronized List<String> read(String name) throws RemoteException{
        return new ArrayList<>(history);
    }

    public void save_Chat() throws RemoteException {  
        try {
            FileWriter writer = new FileWriter("chat_history.txt");
            for(String hist : history) {
                writer.write(hist + System.lineSeparator());
            }
            writer.close();
            System.out.println("Data written to the text file successfully");  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Saving to a text file failed");
        }
    }
    public void load_Chat() throws RemoteException{
        try {
            File file = new File("chat_history.txt");
            

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                
        
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    history.add(line);
                }
                
                sc.close();
            
            } 
            
        } catch (Exception e) {
            System.err.println("Could not load history: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
