package main.java.views;

import java.util.Scanner;

public class LandingView extends AbstractView {

    /**
     * This method renders the opening page view of the application. It is called by
     * Application, which uses its return value to determine which page to navigate to.
     *
     */
    @Override
    public void render() {
        System.out.println("Welcome to the Course Management Portal!");
        System.out.println("If you would like to log in, please enter 'login'.");
        System.out.println("If you would like to register, please enter 'register'.");
        System.out.println("If you would like to exit the portal, please enter 'exit'. " +
                            "You may enter exit at any time to exit the application.");
    }

    /**
     * This method is used to listen for user input. It returns the value to render.
     *
     * @return       the cleaned string the user input into the console
     */
    @Override
    protected String listen() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String input = scanner.nextLine();

            if(!(input.equalsIgnoreCase("login") ||
                 input.equalsIgnoreCase("register") ||
                 input.equalsIgnoreCase("exit"))) {

                System.out.println("You entered an invalid word! We got: '" + input
                                    + "', but we needed one of 'login', 'register', or 'exit'!");
            } else {
                scanner.close();

                return input;
            }
        }
    }
}
