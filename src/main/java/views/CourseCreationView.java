package main.java.views;

import main.java.services.CourseEditService;

import java.util.Scanner;

public class CourseCreationView extends AbstractView {

    private CourseEditService service;

    @Override
    public void render() {
        System.out.println("You are now creating a course...");
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
