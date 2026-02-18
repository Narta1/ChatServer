package Project1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        
        try {
            ChatImpl chat_stub=new ChatImpl();
            //Chat chat_stub=(Chat) UnicastRemoteObject.exportObject(chat, 0);
            Registry register=LocateRegistry.getRegistry();
            register.bind("chatServer", chat_stub);
            
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nShutting down server...");
                try {
                    chat_stub.save_Chat();  
                } catch (Exception e) {
                
                }
               
            }));

            System.out.println ("Server ready");



        } catch (Exception e) {
            System.err.println("Error on server :" + e) ;
            e.printStackTrace();
        }
    }
    
}
