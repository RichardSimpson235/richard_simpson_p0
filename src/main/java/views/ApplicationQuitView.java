package main.java.views;

import java.io.InputStream;

public class ApplicationQuitView extends AbstractView {

    public ApplicationQuitView(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * This method prints out the screen for the user to read.
     */
    @Override
    public void render() {
        System.out.println("====================================================================");
        System.out.println("Thank you for using the Course Management Portal, have a nice day!");
        System.out.println("END");
        System.out.println("====================================================================");
    }

    /**
     * This method doesn't do anything in this class because it doesn't take in user input.
     *
     * @return returns nothing
     */
    @Override
    public String listen() {
        return null;
    }
}
