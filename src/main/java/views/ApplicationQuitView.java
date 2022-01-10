package main.java.views;

import java.io.InputStream;

public class ApplicationQuitView extends AbstractView {

    public ApplicationQuitView(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public void render() {
        System.out.println("Thank you for using the Course Management Portal, have a nice day!");
        System.out.println("END");
        System.out.println("==========================================");
    }

    // This method doesn't do anything in this class because we don't listen
    // for user input.
    @Override
    public String listen() {
        return null;
    }
}
