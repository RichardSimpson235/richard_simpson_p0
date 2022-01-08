package main.java.views;

import main.java.exceptions.AuthenticationException;
import main.java.services.LoginService;

import java.util.Scanner;

public class LoginView extends AbstractView {

    private final LoginService service;

    public LoginView(LoginService service) {
        this.service = service;
    }

    /**
     * This method renders the opening page view of the application. It is called by
     * Application, which uses its return value to determine which page to navigate to.
     *
     * @return    returns a string that decides if the user was a student or a faculty member
     */
    @Override
    public String render() {
        System.out.println("To log in please enter your username and password:");

        return listen();
    }

    /**
     * This method is used to listen for user input. The LoginService is used to authenticate
     * the user. If the user enters 'exit' into the username field, the program leaves.
     *
     * @return       returns a string that decides if the user was a student or a faculty member
     */
    @Override
    protected String listen() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            // Check if they entered exit, to end the program.
            if (username.equalsIgnoreCase("exit")) {
                return username;
            }

            System.out.println();
            System.out.println("Password: ");
            String password = scanner.nextLine();

            try {

                String type = this.service.authenticate(username, password);
                scanner.close();

                return type;

            } catch (AuthenticationException e) {
                System.out.println("Your username and password did not match our records, please try again.");
            }
        }
    }
}