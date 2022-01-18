package main.java.views;

import java.io.InputStream;
import java.util.Scanner;

public abstract class AbstractView {

    protected static Scanner scanner = null;

    public AbstractView(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public void closeScanner() {
        scanner.close();
        scanner = null;
    }

    public abstract void render();
    public abstract String listen();

}
