package com.optimal.network.chatserver;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServerCommunicator extends Thread {

    private Socket sock;					// each instance is in a different thread and has its own socket
    private ChatServer server;				// the main server instance
    private String name;					// client's name (first interaction with server)
    private BufferedReader in;				// from client
    private PrintWriter out;				// to client

    public ChatServerCommunicator(Socket sock, ChatServer server) {
        this.sock = sock;
        this.server = server;
    }

    //List<String> clientsName;
    
    public void run() {
        try {
            System.out.println("someone connected");

            // Communication channel
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);

            // Identify -- first message is the name of my client 
            name = in.readLine();
            System.out.println("it's " + name);
            out.println("Welcom " + name);
            server.broadcast(this, name + " entered the room");
//            
//            clientsName =new ArrayList<>();
//            clientsName.add(name);
//            
            System.out.println("..........."+name);
            
            ChatClient.activeClients(name);

            // Chat away
            String line;
            while ((line = in.readLine()) != null) {
                // String msg = name + " :  " + line;
                //  System.out.println(line);
                server.broadcast(this, line);
            }

            // Done
            System.out.println(name + " hung up");
            server.broadcast(this, name + " left the room");

            // Clean up -- note that also remove self from server's list of handlers so it doesn't broadcast here
            server.removeCommunicator(this);
            out.close();
            in.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sends a message to the client
    public void send(String msg) {
        out.println(msg);
        // out.write(msg);
    }
}
