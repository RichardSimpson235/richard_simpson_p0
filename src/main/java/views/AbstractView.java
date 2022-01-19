package main.java.views;

import java.io.InputStream;
import java.util.Scanner;

public abstract class AbstractView {

    protected static Scanner scanner = null;

    public AbstractView(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    /**
     * This method is used by the Application class
     * to close the input scanner.
     */
    public void closeScanner() {
        scanner.close();
        scanner = null;
    }

    /**
     * This method is used to print out information to the user.
     */
    public abstract void render();

    /**
     * This method is used to listen to user input and process it.
     *
     * @return the key of the next view to navigate to
     */
    public abstract String listen();
}
