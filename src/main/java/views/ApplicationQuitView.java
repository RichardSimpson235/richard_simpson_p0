package main.java.views;

public class ApplicationQuitView extends AbstractView {

    @Override
    public void render() {
        System.out.println("Thank you for using the Course Management Portal, have a nice day!");
        System.out.println("END");
        System.out.println("==========================================");
    }

    // This method doesn't do anything in this class because we don't listen
    // for user input.
    @Override
    protected String listen() {
        return null;
    }
}
