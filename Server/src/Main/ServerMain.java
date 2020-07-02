package Main;

import Core.PlateIDProducer;
import Core.User;
import Core.UsersController;
import Exceptions.PlateOutOfBoundExcepetion;
import Networks.ServerSocketHandler;

import java.util.Objects;
import java.util.Scanner;

public class ServerMain {

    public static void main(String[] args) {
        Thread t = new Thread(new ServerSocketHandler());
        t.start();

        System.out.println("Started listening ...");

        Scanner input = new Scanner(System.in);

        String command;

        while(true){
            command = input.next();

            switch (command){
                case "OnlineUsers":
                    onlineUsers();
                    break;
                case "RegisteredUsers":
                    registeredUsers();
                    break;
                case "next":
                    next(input.next());
                    break;
                case "stopAcceptingUsers":
                    stopAcceptingUsers();
                    break;
                case "resumeAcceptingUsers":
                    resumeAcceptingUsers();
                default:
                    System.out.println("Enter valid command");
            }
        }
    }

    private static void resumeAcceptingUsers() {
        ServerSocketHandler.isAcceptingConnections = true;
        System.out.println("Accepting new connections now");
    }

    private static void stopAcceptingUsers() {
        ServerSocketHandler.isAcceptingConnections = false;
        System.out.println("Denying new connections now");
    }

    private static void next(String scope) {
        Objects.requireNonNull(scope);
        try {
            System.out.println(PlateIDProducer.whatIsNextPlate(PlateIDProducer.Scope.valueOf(scope)));
        } catch (PlateOutOfBoundExcepetion plateOutOfBoundExcepetion) {
            System.out.println("We've run out of plates in this scope :(");
        }

    }

    private static void registeredUsers() {
        System.out.println("Registered users:");
        for (User user: UsersController.getUsers())
            System.out.println(user);
    }

    private static void onlineUsers() {
        System.out.println("Online users:");
        for(User user: UsersController.onlineUsers)
           System.out.println(user);
    }
}
