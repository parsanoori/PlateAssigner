package Main;// Java implementation for a client
// Save file as Client.java

import NetworkHandlers.InputHandler;
import NetworkHandlers.OutputHandler;

import java.io.*;
import java.net.*;

// Client class
public class ClientMain
{
    public static void main(String[] args) throws IOException
    {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 7080);

            Thread input = new Thread(new InputHandler(s.getInputStream(),s));
            Thread output = new Thread(new OutputHandler(s.getOutputStream()));

            input.start();
            output.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

