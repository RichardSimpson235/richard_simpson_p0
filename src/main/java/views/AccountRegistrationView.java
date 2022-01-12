package main.java.views;

import main.java.exceptions.RegistrationFailedException;
import main.java.services.AccountService;

import java.io.InputStream;
import java.util.Scanner;

public class AccountRegistrationView extends AbstractView {

    private final AccountService service;

    public AccountRegistrationView(InputStream inputStream, AccountService service) {
        super(inputStream);
        this.service = service;
    }

    @Override
    public void render() {
        System.out.println("Welcome to account registration! Please answer the following questions:");
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        System.out.println("What is your first name?");
        String firstName = scanner.nextLine();
        if(firstName.equalsIgnoreCase("exit")) {
            return "exit";
        }

        System.out.println("What is your last name?");
        String lastName = scanner.nextLine();
        if(lastName.equalsIgnoreCase("exit")) {
            return "exit";
        }

        System.out.println("What is your age?");
        String age = scanner.nextLine();
        if(age.equalsIgnoreCase("exit")) {
            return "exit";
        }

        System.out.println("What should your username be?");
        String username = scanner.nextLine();
        if(username.equalsIgnoreCase("exit")) {
            return "exit";
        }

        System.out.println("What should your password be?");
        String password = scanner.nextLine();
        if(password.equalsIgnoreCase("exit")) {
            return "exit";
        }

        try {
            service.register(firstName, lastName, age, username, password);
        } catch (RegistrationFailedException e) {
            System.out.println("We're sorry but it seems we had some trouble creating your account. Please try again.");
        }
        scanner.close();

        return "landing";
    }
}
