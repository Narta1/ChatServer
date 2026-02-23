import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientGUI {
    
    private JFrame frame;
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;
    
    private Chat server;
    private String username;
    
    public ClientGUI(String host, String username) {
        this.username = username;
        
        // Create the frame
        frame = new JFrame("Chat - " + username);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the message display area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Create the input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);
        
        // Add listeners
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
        
        // Connect to RMI server
        connectToServer(host);
        
        frame.setVisible(true);
    }
    
    private void connectToServer(String host) {
        try {
        
            ChatClientGUI clientCallback = new ChatClientGUI(this);
            

            Registry registry = LocateRegistry.getRegistry(host);
            server = (Chat) registry.lookup("chatServer");
            
            
            server.join(username, clientCallback);
            displayMessage("*** Connected to chat server ***");
            
        
            for (String msg : server.read(username)) {
                displayMessage(msg);
            }
            displayMessage("*** End of history ***");
            
        } catch (Exception e) {
            displayMessage("ERROR: Could not connect to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            try {
                server.write(username, message);
                inputField.setText("");
            } catch (Exception e) {
                displayMessage("ERROR: Could not send message: " + e.getMessage());
            }
        }
    }
    
    public void displayMessage(String message) {
        messageArea.append(message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ClientGUI <host> <username>");
            return;
        }
        
        String host = args[0];
        String username = args[1];
        
        SwingUtilities.invokeLater(() -> {
            new ClientGUI(host, username);
        });
    }
}
