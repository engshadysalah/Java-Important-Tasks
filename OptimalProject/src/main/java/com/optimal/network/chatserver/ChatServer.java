package com.optimal.network.chatserver;

import java.net.*;
import java.util.*;
import java.io.*;

public class ChatServer {

    private ServerSocket listen;						// for accepting connections
    private ArrayList<ChatServerCommunicator> comms;	// all the connections with clients

    public ChatServer(ServerSocket listen) {
        this.listen = listen;
        comms = new ArrayList<ChatServerCommunicator>();
    }

    // The usual loop of accepting connections and firing off new threads to handle them
    public void getConnections() throws IOException {
        while (true) {
            ChatServerCommunicator comm = new ChatServerCommunicator(listen.accept(), this);
            comm.setDaemon(true);
            comm.start();
            addCommunicator(comm);
        }
    }

    /**
     * Adds the handler to the list of current client handlers
     */
    public synchronized void addCommunicator(ChatServerCommunicator comm) {
        comms.add(comm);

     }

    /**
     * Removes the handler from the list of current client handlers
     */
    public synchronized void removeCommunicator(ChatServerCommunicator comm) {
        comms.remove(comm);
    }

    /**
     * Sends the message from the one client handler to all the others (but not
     * echoing back to the originator)
     */
    int sender_id = 0;

    public synchronized void broadcast(ChatServerCommunicator from, String msg) {


        for (ChatServerCommunicator c : comms) {
            if (c == from) {
                sender_id = comms.indexOf(c);
                break;
            }

        }

        for (ChatServerCommunicator c : comms) {

            System.out.println("Test " + msg);

            if (msg.contains("private")) {

                String[] privateMessade = msg.split("private to ");

                System.out.println("Test " + privateMessade[0]);
                System.out.println("Test " + privateMessade[1]);

              // try{
                if (comms.size() <= (Integer.parseInt(privateMessade[1]))) {
                    from.send("Client " + privateMessade[1] + "not founed ");
                    break;
                } else {
                    if (c == comms.get(Integer.parseInt(privateMessade[1]))) {

                        c.send(" From Client  " + sender_id + ": " + privateMessade[0]);
                        from.send("to client  " + Integer.parseInt(privateMessade[1]) + ": " + privateMessade[0]);

                    }
                }
                
             //  }catch(NumberFormatException ex){
                   System.out.println("");
                  //  c.send("Enter name firest please");
            //   }
            } else {

                if (from == c) {
                    c.send(msg);

                } else {
                    c.send(msg + " : From Client " + sender_id);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("waiting for connections");
        new ChatServer(new ServerSocket(4242)).getConnections();

    }
}
