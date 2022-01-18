package main.java.views;

import main.java.exceptions.MissingAccountException;
import main.java.services.AccountService;

import java.io.InputStream;
import java.util.Scanner;

public class LoginView extends AbstractView {

    private final AccountService service;

    public LoginView(InputStream inputStream, AccountService service) {
        super(inputStream);
        this.service = service;
    }

    /**
     * This method renders the opening page view of the application. It is called by
     * Application, which uses its return value to determine which page to navigate to.
     *
     */
    @Override
    public void render() {
        System.out.println("To log in please enter your username and password:");
    }

    /**
     * This method is used to listen for user input. The AccountService is used to authenticate
     * the user. If the user enters 'exit' into the username field, the program leaves.
     *
     * @return       returns a string that decides if the user was a student or a faculty member
     */
    @Override
    public String listen() {

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
            if (password.equalsIgnoreCase("exit")) {
                return password;
            }

            try {

                String type = this.service.authenticate(username, password);

                return type;

            } catch (MissingAccountException e) {
                System.out.println("Your username and password did not match our records, please try again.");
            }
        }
    }
}
