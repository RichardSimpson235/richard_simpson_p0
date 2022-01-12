package main.java.views;

import main.java.exceptions.FailedEnrollmentException;
import main.java.exceptions.InvalidDataException;
import main.java.models.Course;
import main.java.services.CourseService;

import java.io.InputStream;
import java.util.Scanner;

public class CourseDetailView extends AbstractView {

    private final Course course;
    private final CourseService service;

    public CourseDetailView(InputStream inputStream, CourseService service) {
        super(inputStream);
        this.service = service;

        this.course = service.getCourse();
    }

    @Override
    public void render() {
        System.out.println("Detailed Class Viewer");
        System.out.println(this.course);
    }

    @Override
    public String listen() {

        if(this.service.isUserFaculty()) {
            System.out.println("If you would like to edit a field, enter the field's name (for example: 'name').");
            System.out.println("If you would like to delete the class please enter 'delete'.");
        } else {
            if(this.service.isUserEnrolled()) {
                System.out.println("If you would like to unenroll from this class type 'unenroll'.");
            } else {
                System.out.println("If you would like to enroll in this class, type 'enroll'");
            }
        }
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {
                scanner.close();

                return input;
            } else if(input.equalsIgnoreCase("unenroll")) {
                try {
                    service.unenrollUser();
                } catch (FailedEnrollmentException e) {
                    System.out.println("Sorry, we were not able to unenroll you in that class.");
                }
                scanner.close();

                return "student";
            } else if(input.equalsIgnoreCase("enroll")) {
                try {
                    service.enrollUser();
                } catch (FailedEnrollmentException e) {
                    System.out.println("Sorry, we were not able to enroll you in that class.");
                }
                scanner.close();

                return "student";
            } else if(input.equalsIgnoreCase("delete")) {
                service.deleteCourse();
                System.out.println("Course: " + course.getName() + " was deleted!");
                scanner.close();

                return this.service.isUserFaculty() ? "faculty" : "student";
            } else if(course.isValidField(input)) {
                System.out.println("What would you like to change it to?");
                String newFieldData = scanner.nextLine();

                try {
                    service.editCourse(input, newFieldData);
                    scanner.close();

                    return this.service.isUserFaculty() ? "faculty" : "student";
                } catch(InvalidDataException e) {
                    System.out.println("The data you entered seems to be invalid. Please check it again.");
                }
            } else {
                System.out.println("You entered an invalid option. We got: " + input + ", please check the valid inputs" +
                        " again.");
            }
        }
    }
}
