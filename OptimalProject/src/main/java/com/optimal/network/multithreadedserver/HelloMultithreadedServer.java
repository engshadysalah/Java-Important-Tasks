/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.network.multithreadedserver;

/**
 *
 * @author root
 */
import java.net.*;
import java.io.*;

/**
 * Multithreaded version of the hello server Now lets multiple someones connect
 * on port 4242; for each one, it repeatedly asks for their name and greets
 * them. Connect either by "telnet localhost 4242" or by running
 * HelloClient.java (multiple times)
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised 2014 to
 * split out HelloServerCommunicator
 */
public class HelloMultithreadedServer {

    private ServerSocket listen;	// where clients initially connect

    public HelloMultithreadedServer(ServerSocket listen) {
        this.listen = listen;
    }

    /**
     * Listens to listen and fires off new communicators to handle the clients
     */
    public void getConnections() throws IOException {
        System.out.println("waiting for someone to connect");

        // Just keep accepting connections and firing off new threads to handle them.
        int num = 0;
        while (true) {
            HelloServerCommunicator client = new HelloServerCommunicator(listen.accept(), num++);
            client.setDaemon(true); // handler thread terminates when main thread does
           
            client.start();
        }

    }

    public static void main(String[] args) throws IOException {
        new HelloMultithreadedServer(new ServerSocket(4242)).getConnections();
    }
}
