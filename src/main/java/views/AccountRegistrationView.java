package main.java.views;

import main.java.services.RegistrationService;

import java.io.InputStream;
import java.util.Scanner;

public class AccountRegistrationView extends AbstractView {

    private final RegistrationService service;

    public AccountRegistrationView(InputStream inputStream, RegistrationService service) {
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

        service.register(firstName, lastName, age, username, password);
        scanner.close();

        return "student";
    }
}
