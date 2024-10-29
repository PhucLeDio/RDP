package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer extends Thread {
    private static final Set<PrintWriter> clientWriters = new HashSet<>();
    private Socket socket;

    public ChatServer(Socket socket) {
        this.socket = socket;
        start(); // Start the thread
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            synchronized (clientWriters) {
                clientWriters.add(writer); // Add the writer to the set
            }

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received: " + message);
                // Broadcast the message to all clients
                synchronized (clientWriters) {
                    for (PrintWriter clientWriter : clientWriters) {
                        clientWriter.println(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (clientWriters) {
                clientWriters.remove(socket);
            }
        }
    }
}


