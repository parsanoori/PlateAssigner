package Networks;

import Core.Plate;
import Core.PlateIDProducer;
import Core.UsersController;
import Core.User;
import Exceptions.PlateOutOfBoundExcepetion;
import Exceptions.UserAlreadyExistsException;
import Tools.ObjectsExtensions;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Scanner input;
    private final PrintStream output;

    private User user = null;

    public ClientHandler(Socket socket, InputStream inputStream, OutputStream outputStream) {
        ObjectsExtensions.requireNonNulls(inputStream,outputStream,socket);
        this.socket = socket;
        this.input = new Scanner(inputStream);
        this.output = new PrintStream(outputStream);
    }

    @Override
    public void run() {
        String command;
        while (true)
        {
            try {
                output.println("Enter your command:");

                command = input.next();

                if(command.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                switch (command){
                    case "Newuser":
                        addNewUser();
                        break;
                    case "Login":
                        login();
                        break;
                    case "Logout":
                        logout();
                        break;
                    case "GetPlate":
                        getPlate();
                        break;
                    case "transferPlate":
                        transferPlate();
                        break;
                    case "WhatIsMyPlate":
                        whatsMyPlate();
                        break;
                    default:
                        output.println("Command not recognised. Please try again.");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        this.input.close();
        this.output.close();

    }

    private void whatsMyPlate() {
        if (user.hasPlate())
            output.println("You're plate ID is: " + user.id);
        else
            output.println("You ain't got any plate. Get one soon!");
    }

    private void logout() {
        UsersController.onlineUsers.remove(user);
        user = null;
        output.println("You are now logged out!");
    }

    private void login() {
        String id = input.next();
        user = UsersController.getUserByID(id);
        UsersController.onlineUsers.add(user);
        output.println("You've logged in. Welcome!");
    }

    private void getPlate() {
        if (user.hasPlate()){
            output.println("You already have plate. Be hamin yeki ghane bash. Ziade khahi bade. Ehtekar ham nakon.");
            return;
        }
        try {
            String plateIO = PlateIDProducer.getNewPlateID(user.scope);
            user.setPlateOwned(new Plate(user,plateIO));
            output.println("You've got a new plate with ID : " + plateIO);
        } catch (PlateOutOfBoundExcepetion plateOutOfBoundExcepetion) {
            output.println("We've run out of plates. Tehran has turned to a garage.");
        }
    }

    private void transferPlate() {
        String destinationUserId = input.next();
        User destinationUser = UsersController.getUserByID(destinationUserId);
        if (!user.hasPlate()){
            output.println("You ain't got any plate.");
            return;
        }
        if (destinationUser == null) {
            output.println("There's no user with that ID please try again.");
            return;
        }
        if (destinationUser.scope != user.scope){
            output.println("Destination user scope doesn't match yours. Try again please");
            return;
        }
        if (destinationUser.hasPlate()){
            output.println("The destination user already has a plate. BTW thanks for your mercy.");
            return;
        }

        destinationUser.setPlateOwned(user.getPlateOwned());
        user.removePlate();
        output.println("The plate moved to user with id :" + destinationUser.id + " and name: " + destinationUser.name + ".");
    }

    private void addNewUser() {
        if (user != null){
            output.println("You are already logged in! Please logout. Then you can sign up :)");
            return;
        }
        String name  = input.next();
        String lastName = input.next();
        String id = input.next();
        String scope = input.next();
        User user = new User(name,lastName,id,scope);
        try {
            UsersController.addUser(user);
            output.println("The user with ID: " + user.id + " has logged in");
        } catch (UserAlreadyExistsException e) {
            output.println("This user already exists!");
            output.println("You can login if you want!");
        }

    }
}
