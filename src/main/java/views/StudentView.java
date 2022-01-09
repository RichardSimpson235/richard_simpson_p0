package main.java.views;

import main.java.collections.List;
import main.java.models.Course;
import main.java.services.StudentService;

import java.io.InputStream;
import java.util.Scanner;

public class StudentView extends AbstractView {

    private final StudentService service;
    private final List<Integer> validSelections;

    public StudentView(InputStream inputStream, StudentService service) {
        super(inputStream);
        this.service = service;

        this.validSelections = new List<>();
    }

    @Override
    public void render() {
        System.out.println("==========================================");
        List<Course> courses = service.getCourses();

        System.out.println("Welcome " + service.getStudentName() + "!");

        if(courses.isEmpty()) {
            System.out.println("You have not enrolled in any courses yet.");
        } else {
            System.out.println("You are enrolled in the following courses:");
            for(int i = 1; i <= courses.size(); i++) {
                System.out.println(i + ". " + courses.get(i - 1));
                validSelections.add(i);
            }

            System.out.println("If you would like to see the details of a class please enter 'view'.");
            System.out.println("If you would like to enroll in a class please enter 'enroll'.");
        }
    }

    @Override
    protected String listen() {
        Scanner scanner = new Scanner(this.inputStream);
        List<Course> courses = service.getCourses();

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("enroll")) {
                scanner.close();

                return input;
            } else if(input.equalsIgnoreCase("view")) {
                System.out.println("Please enter an integer of the class list (for example press 1 for " +
                                    courses.get(0) + (courses.size() > 1 ? ", 2 for " + courses.get(1) + ", etc)." : ""));

                input = scanner.nextLine();
                try {
                    int index = Integer.parseInt(input);

                    if(!validSelections.contains(index)) {
                        System.out.println("You entered an invalid number! We got: " + input +
                                ", we need one of: " + validSelections);
                    } else {
                        String courseName = service.getCourses().get(index).name;
                        System.out.println("You've selected: " + input + ". " + courseName + ". Is this correct? (y/n)");

                        input = scanner.nextLine();
                        if(input.equalsIgnoreCase("y")) {
                            scanner.close();

                            return String.valueOf(index);
                        } else if(!input.equalsIgnoreCase("n")) {
                            System.out.println("Please enter 'y' or 'n' for yes or no.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("It seems you didn't enter an integer. Please enter an integer.");
                }
            }
        }
    }
}
