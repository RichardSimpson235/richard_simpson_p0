package main.java.views;

import main.java.exceptions.RegistrationFailedException;
import main.java.services.AccountService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class AccountRegistrationView extends AbstractView {

    private final AccountService service;

    public AccountRegistrationView(InputStream inputStream, AccountService service) {
        super(inputStream);
        this.service = service;
    }

    /**
     * This method prints out the screen for the user to read.
     */
    @Override
    public void render() {
        System.out.println("Welcome to account registration! Please answer the following questions:");
    }

    /**
     * This method listens for user input. The user can enter 'exit' at any time to quit the application.
     * An example of how this page is used is as follows (A stands for the application, U for the user):
     * =========================================================================================================
     * (A)-> What is your first name?
     * (U)-> Richard
     * (A)-> What is your last name?
     * (U)-> Simpson
     * (A)-> What is your date of birth? Please enter the date in the following format: MM/dd/yyyy
     * (U)-> 01/01/1993
     * (A)-> What should your username be?
     * (U)-> rsimpson
     * (A)-> What should your password be?
     * (U)-> password
     * (A)-> What would you like your meal plan tier to be? Please enter an integer between 0 and 3, inclusive.
     * (U)-> 0
     * (A)-> What is your major? Please limit your response to less than 20 characters.
     * (U)-> Computer Science
     * =========================================================================================================
     * Where the user could make a mistake the system prompts them again.
     *
     *
     * @return the next view to navigate to
     */
    @Override
    public String listen() {

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

        System.out.println("What is your email");
        String email = scanner.nextLine();
        if(email.equalsIgnoreCase("exit")) {
            return "exit";
        }

        String dateFormatPattern = "MM/dd/yyyy";
        System.out.println("What is your date of birth? Please enter the date in the following format: " + dateFormatPattern);
        long dateOfBirth;
        while(true) {
            String date = scanner.nextLine();
            if(date.equalsIgnoreCase("exit")) {
                return "exit";
            }

            try {
                DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
                dateOfBirth = dateFormat.parse(date).getTime();
                break;
            } catch (ParseException e) {
                System.out.println("The date you entered appears to be in the wrong format, please try again.");
            }
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

        System.out.println("What would you like your meal plan tier to be? Please enter an integer between 0 and 3, inclusive.");
        int mealPlanTier;
        while(true) {
            String tier = scanner.nextLine();
            if(tier.equalsIgnoreCase("exit")) {
                return "exit";
            }

            try {
                mealPlanTier = Integer.parseInt(tier);

                if(mealPlanTier < 0 || mealPlanTier > 3) {
                    System.out.println("The number you entered is too big! It must either be 1, 2, or 3.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("The tier you entered seems to not be a number, please check again.");
            }
        }

        System.out.println("What is your major? Please limit your response to less than 20 characters.");
        String major = scanner.nextLine();
        if(major.equalsIgnoreCase("exit")) {
            return "exit";
        }

        try {
            service.register(firstName, lastName, email, dateOfBirth, username, password, mealPlanTier, major);
        } catch (RegistrationFailedException e) {
            System.out.println("We're sorry but it seems we had some trouble creating your account. Please try again.");
        }

        return "landing";
    }
}
