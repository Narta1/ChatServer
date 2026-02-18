package Project1;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.rmi.server.UnicastRemoteObject;

public class Client {
    public static void main(String[] args) {
        try {
             if (args.length < 2) {
             System.out.println("Usage: java HelloClient <rmiregistry host> <username>");
             return;}

        String host = args[0];
        String username=args[1];
        Registry registry=LocateRegistry.getRegistry(host);
        Chat server_stub=(Chat) registry.lookup("chatServer");
        ChatClientImpl c_stub=new ChatClientImpl(username);



        server_stub.join(username, c_stub);

        System.out.println("Joined the chat as " + username);
        List<String> history=server_stub.read(username);
        if(!history.isEmpty()){
            System.out.println("\n--- Message History ---");
            for(String msg : history){
                System.out.println(msg);
            }
            System.out.println("--- End of History ---\n");
        }
        
        Scanner sc=new Scanner(System.in);
        System.out.println("Type you message or type 'quit' to leave chat");

        while(true){
            
            String message = sc.nextLine();
            if(message.equals("quit")){
                server_stub.leave(username);
                System.out.println("leaving the chat");
                UnicastRemoteObject.unexportObject(c_stub, true);
                break;
            }
            server_stub.write(username, message);
            
        }
        sc.close();
        System.exit(0);
        
    }

catch (Exception e) {
           System.err.println("Error on client: " + e) ;
       }

    }}