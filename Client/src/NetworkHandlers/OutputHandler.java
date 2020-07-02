package NetworkHandlers;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class OutputHandler implements Runnable {

    private final PrintStream printStream;
    private final Scanner stdin = new Scanner(System.in);

    public OutputHandler(OutputStream outputStream) {
        this.printStream = new PrintStream(outputStream);
    }

    @Override
    public void run() {
        while (true){
            try {
                String userInput = stdin.nextLine();
                printStream.println(userInput);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
