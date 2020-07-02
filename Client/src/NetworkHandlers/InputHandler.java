package NetworkHandlers;

import Tools.ObjectsExtensions;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class InputHandler implements Runnable {

    private final Scanner input;
    private final Socket socket;

    public InputHandler(InputStream inputStream, Socket socket) {
        ObjectsExtensions.requireNonNulls(inputStream,socket);
        this.input = new Scanner(inputStream);
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String received = input.nextLine();
                System.out.println(received);
            } catch (Exception e) {
                System.out.println("Lost Connection Try again.");
                break;
            }
        }

        try {
            socket.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exit(10);
        }
    }
}
