package main.java.views;

import main.java.exceptions.CreationFailedException;
import main.java.services.AccountService;
import main.java.services.CourseService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CourseCreationView extends AbstractView {

    private final AccountService accountService;
    private final CourseService courseService;

    public CourseCreationView(InputStream inputStream, AccountService accountService, CourseService courseService) {
        super(inputStream);
        this.accountService = accountService;
        this.courseService = courseService;
    }

    @Override
    public void render() {
        System.out.println("You are now creating a course...");
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        System.out.println("What would you like to name this class?");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("exit")) {
            scanner.close();

            return name;
        }

        System.out.println("Please enter a description for this class:");
        String description = scanner.nextLine();
        if (description.equalsIgnoreCase("exit")) {
            scanner.close();

            return description;
        }

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("When does enrollment for this class start? Enter date: MM/dd/yyyy");
        long enrollmentStartDateL;
        while(true) {
            String enrollmentStartDate = scanner.nextLine();
            if (enrollmentStartDate.equalsIgnoreCase("exit")) {
                scanner.close();

                return enrollmentStartDate;
            }

            try {
                enrollmentStartDateL = dateFormat.parse(enrollmentStartDate).getTime();
                break;
            } catch (ParseException e) {
                System.out.println("It seems the date you entered was formatted incorrectly, please try again.");
            }
        }

        System.out.println("When does enrollment for this class end? Enter date: MM/dd/yyyy");
        long enrollmentEndDateL;
        while(true) {
            String enrollmentEndDate = scanner.nextLine();
            if (enrollmentEndDate.equalsIgnoreCase("exit")) {
                scanner.close();

                return enrollmentEndDate;
            }

            try {
                enrollmentEndDateL = dateFormat.parse(enrollmentEndDate).getTime();
                break;
            } catch (ParseException e) {
                System.out.println("It seems the date you entered was formatted incorrectly, please try again.");
            }
        }

        System.out.println("How many credits is this course worth?");
        int credits;
        while(true) {
            String creditInput = scanner.nextLine();
            if (creditInput.equalsIgnoreCase("exit")) {
                scanner.close();

                return creditInput;
            }

            try {
                credits = Integer.parseInt(creditInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please be sure to only enter an integer, for example '1'");
            }
        }

        try {
            courseService.createCourse(name, description, enrollmentStartDateL, enrollmentEndDateL, credits);
        } catch (CreationFailedException e) {
            System.out.println("We failed to create the course, you may need to try again later.");
        }

        return "faculty";
    }
}
