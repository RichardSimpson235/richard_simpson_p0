package main.java.views;

import main.java.services.CourseEditService;

import java.io.InputStream;
import java.util.Scanner;

public class CourseCreationView extends AbstractView {

    private final CourseEditService service;

    public CourseCreationView(InputStream inputStream, CourseEditService service) {
        super(inputStream);
        this.service = service;
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

        System.out.println("How long is the enrollment period? Please enter a number of days.");
        String enrollmentPeriod = scanner.nextLine();

        int enrollmentPeriodInt = 0;
        if (enrollmentPeriod.equalsIgnoreCase("exit")) {
            scanner.close();

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
        scanner.close();

        return "faculty";
    }
}
