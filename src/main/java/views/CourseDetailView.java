package main.java.views;

import main.java.exceptions.InvalidDataException;
import main.java.models.Course;
import main.java.models.Student;
import main.java.models.User;
import main.java.services.CourseEditService;

import java.io.InputStream;
import java.util.Scanner;

public class CourseDetailView extends AbstractView {

    private final User user;
    private final Course course;
    private final CourseEditService service;

    public CourseDetailView(InputStream inputStream, CourseEditService service, User user) {
        super(inputStream);
        this.service = service;
        this.user = user;

        this.course = service.getCourse();
    }

    @Override
    public void render() {
        System.out.println("Detailed Class Viewer");
        System.out.println(this.course);
    }

    @Override
    public String listen() {

        if(this.user.isFaculty()) {
            System.out.println("If you would like to edit a field, enter the field's name (for example: 'name').");
            System.out.println("If you would like to delete the class please enter 'delete'.");
        } else {
            System.out.println("If you would like to unenroll from this class type 'unenroll'.");
        }
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {
                scanner.close();

                return input;
            } else if(input.equalsIgnoreCase("unenroll")) {
                service.unenrollStudent((Student) user);

                return "student";
            } else if(input.equalsIgnoreCase("delete")) {
                service.deleteCourse(this.course);
                System.out.println("Course: " + course.name + " was deleted!");
                scanner.close();

                return this.user.isFaculty() ? "faculty" : "student";
            } else if(course.isValidField(input)) {
                System.out.println("What would you like to change it to?");
                String newFieldData = scanner.nextLine();

                try {
                    service.editCourse(course.ID, input, newFieldData);
                    scanner.close();

                    return this.user.isFaculty() ? "faculty" : "student";
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
