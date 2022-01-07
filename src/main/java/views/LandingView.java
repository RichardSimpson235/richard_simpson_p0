package main.java.views;

import java.util.Scanner;

public class LandingView extends AbstractView {

    @Override
    public String render() {
        System.out.println("Welcome to the Course Management Portal!");
        System.out.println("If you would like to log in, please enter 'login'.");
        System.out.println("If you would like to register, please enter 'register'.");
        System.out.println("If you would like to exit the portal, please enter 'exit'. " +
                            "You may enter exit at any time to exit the application.");

        return listen();
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String input = scanner.nextLine();

            if(!(input.equalsIgnoreCase("login") ||
                 input.equalsIgnoreCase("register") ||
                 input.equalsIgnoreCase("exit"))) {

                System.out.println("You entered an invalid word! We got: '" + input
                                    + "', but we needed one of 'login', 'register', or 'exit'!");
            } else {
                return input;
            }
        }
    }
}
