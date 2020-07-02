package Networks;
// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import Core.User;

import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class ServerSocketHandler implements Runnable
{
    static public boolean isAcceptingConnections = true;

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(7080);
            while (isAcceptingConnections) {
                try {
                    Socket s = ss.accept();
                    Thread t = new Thread(new ClientHandler(s, s.getInputStream(), s.getOutputStream()));
                    t.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


