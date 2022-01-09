package main.java.views;

import main.java.exceptions.InvalidDataException;
import main.java.models.Course;
import main.java.services.CourseEditService;

import java.util.Scanner;

public class CourseDetailView extends AbstractView {

    private final boolean isFaculty;
    private final Course course;
    private final CourseEditService service;

    public CourseDetailView(CourseEditService service,  boolean isFaculty) {
        this.service = service;
        this.isFaculty = isFaculty;

        this.course = service.getCourse();
    }

    @Override
    public void render() {
        System.out.println("Detailed Class Viewer");
        System.out.println(this.course);
    }

    @Override
    protected String listen() {
        System.out.println("If you would like to edit a field, enter the field's name (for example: 'name').");
        System.out.println("If you would like to delete the class please enter 'delete'.");
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {
                return input;
            } else if(input.equalsIgnoreCase("delete")) {
                service.deleteCourse(this.course);
                System.out.println("Course: " + course.name + " was deleted!");

                return isFaculty ? "faculty" : "student";
            } else if(course.isValidField(input)) {
                System.out.println("What would you like to change it to?");
                String newFieldData = scanner.nextLine();

                try {
                    service.editCourse(course.ID, input, newFieldData);

                    return isFaculty ? "faculty" : "student";
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
