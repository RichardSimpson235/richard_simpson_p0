package main.java.views;

import main.java.services.CourseCreationService;

import java.util.Scanner;

public class CourseCreationView extends AbstractView {

    private CourseCreationService service;

    @Override
    public String render() {
        System.out.println("You are now creating a course...");

        return listen();
    }

    @Override
    protected String listen() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to name this class?");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("exit")) {
            return name;
        }

        System.out.println("How long is the enrollment period? Please enter a number of days.");
        String enrollmentPeriod = scanner.nextLine();

        int enrollmentPeriodInt = 0;
        if (enrollmentPeriod.equalsIgnoreCase("exit")) {
            return enrollmentPeriod;

        } else {
            try {
                enrollmentPeriodInt = Integer.parseInt(enrollmentPeriod);

            } catch (NumberFormatException e) {
                System.out.println("It appears you've entered invalid input. We got: " + enrollmentPeriod +
                                    ", but we can only take integers (ex: 3).");
            }
        }

        service.createCourse(name, enrollmentPeriodInt);

        return "faculty";
    }
}
