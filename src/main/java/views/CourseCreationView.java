package main.java.views;

import main.java.services.CourseService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CourseCreationView extends AbstractView {

    private final CourseService service;

    public CourseCreationView(InputStream inputStream, CourseService service) {
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

        while (true) {
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

            System.out.println("When does enrollment for this class start? Enter date: MM/dd/yyyy");
            String enrollmentStartDate = scanner.nextLine();
            if (enrollmentStartDate.equalsIgnoreCase("exit")) {
                scanner.close();

                return enrollmentStartDate;
            }

            System.out.println("When does enrollment for this class end? Enter date: MM/dd/yyyy");
            String enrollmentEndDate = scanner.nextLine();
            if (enrollmentEndDate.equalsIgnoreCase("exit")) {
                scanner.close();

                return enrollmentEndDate;
            }

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            try {
                long enrollmentStartDateLong = dateFormat.parse(enrollmentStartDate).getTime();
                long enrollmentEndDateLong = dateFormat.parse(enrollmentEndDate).getTime();

                service.createCourse(name, description, enrollmentStartDateLong, enrollmentEndDateLong);

                scanner.close();
                return "faculty";
            } catch (ParseException e) {
                System.out.println("The dates seems to have been in an invalid format, please try again!");
            }
        }
    }
}
