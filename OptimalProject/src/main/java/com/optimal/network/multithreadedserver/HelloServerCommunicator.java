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
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles communication with one client for HelloMultiThreadedServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised 2014 to
 * split out HelloServerCommunicator
 */
public class HelloServerCommunicator extends Thread {

    private Socket sock = null;		// to talk with client
    private int id;					// for marking the messages (just for clarity in reading console)

    public HelloServerCommunicator(Socket sock, int id) {
        this.sock = sock;
        this.id = id;
    }

    /**
     * The body of the thread is basically the same as what we had in main() of
     * the single-threaded version
     */
    @Override
    public void run() {
        // Smother any exceptions, to match the signature of Thread.run()
        try {
            System.out.println("#" + id + " connected");

            // Communication channel
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            // Talk
            out.println("who is it?");
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("#" + id + " received:" + line);
                out.println("server:  " + line + "");
            }
            System.out.println("#" + id + " Close The Connection");

            // Clean up
            out.close();
            in.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
