package main.java.views;

import main.java.exceptions.CreationFailedException;
import main.java.services.AccountService;
import main.java.services.CourseService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CourseCreationView extends AbstractView {

    private final AccountService accountService;
    private final CourseService courseService;

    public CourseCreationView(InputStream inputStream, AccountService accountService, CourseService courseService) {
        super(inputStream);
        this.accountService = accountService;
        this.courseService = courseService;
    }

    /**
     * This method prints out the screen for the user to read.
     */
    @Override
    public void render() {
        System.out.println("You are now creating a course...");
    }

    /**
     * This method listens for user input. The user can enter 'exit' at any time to quit the application.
     * An example of how this page is used is as follows (A stands for the application, U for the user):
     * =========================================================================================================
     * (A)-> What would you like to name this class?
     * (U)-> Linear Algebra
     * (A)-> Please enter a description for this class:
     * (U)-> description
     * (A)-> When does enrollment for this class start? Enter date: MM/dd/yyyy
     * (U)-> 01/01/1993
     * (A)-> When does enrollment for this class end? Enter date: MM/dd/yyyy
     * (U)-> 01/01/1994
     * (A)-> How many credits is this course worth?
     * (U)-> 3
     * =========================================================================================================
     * Where the user could make a mistake the system prompts them again.
     *
     * @return the next view to navigate to
     */
    @Override
    public String listen() {

        System.out.println("What would you like to name this class?");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("exit")) {

            return name;
        }

        System.out.println("Please enter a description for this class:");
        String description = scanner.nextLine();
        if (description.equalsIgnoreCase("exit")) {

            return description;
        }

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("When does enrollment for this class start? Enter date: MM/dd/yyyy");
        long enrollmentStartDateL;
        while(true) {
            String enrollmentStartDate = scanner.nextLine();
            if (enrollmentStartDate.equalsIgnoreCase("exit")) {

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
            accountService.assign(courseService.getCourse());
        } catch (CreationFailedException e) {
            System.out.println("We failed to create the course, you may need to try again later.");
            System.out.println();
        }

        return "faculty";
    }
}
