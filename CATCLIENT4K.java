import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LunarClient {
    private String serverAddress;
    private int serverPort;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public LunarClient(String address, int port) {
        this.serverAddress = address;
        this.serverPort = port;
    }

    // Connect to the server
    public void connect() {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
    }

    // Send a message to the server
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        } else {
            System.err.println("Not connected to server.");
        }
    }

    // Receive a message from the server
    public void receiveMessages() {
        new Thread(() -> {
            String response;
            try {
                while ((response = in.readLine()) != null) {
                    System.out.println("Server: " + response);
                }
            } catch (IOException e) {
                System.err.println("Error receiving message: " + e.getMessage());
            }
        }).start();
    }

    // Disconnect from the server
    public void disconnect() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }

    // Main method for demonstration
    public static void main(String[] args) {
        LunarClient client = new LunarClient("127.0.0.1", 12345);
        client.connect();
        client.receiveMessages();
        client.sendMessage("Hello, server!");
        
        // Add a delay to see the server response before disconnecting
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
a        
        client.disconnect();
    }
}

/*
This code is a basic Java client implementation that can connect to a game server (or any server).
- `connect()` establishes a connection to the server.
- `sendMessage()` sends a message to the server.
- `receiveMessages()` listens for and prints messages from the server.
- `disconnect()` closes the connection.

In a real "Lunar Client" for Minecraft, you'd be looking at much more complex tasks, such as handling game packets, rendering, and player authentication.
*/
