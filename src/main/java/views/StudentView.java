package main.java.views;

import main.java.structures.List;
import main.java.models.Course;
import main.java.services.AccountService;

import java.io.InputStream;
import java.util.Scanner;

public class StudentView extends AbstractView {

    private final AccountService service;

    public StudentView(InputStream inputStream, AccountService service) {
        super(inputStream);
        this.service = service;
    }

    @Override
    public void render() {
        System.out.println("==========================================");
        List<Course> courses = service.getCourses();

        System.out.println("Welcome " + service.getAccountName() + "!");

        if(courses.isEmpty()) {
            System.out.println("You have not enrolled in any courses yet.");
        } else {
            System.out.println("You are enrolled in the following courses:");
            for(int i = 1; i <= courses.size(); i++) {
                System.out.println(i + ". " + courses.get(i - 1));
            }

            if (courses.size() != 0) {
                System.out.println("If you would like to see the details of a class please enter 'view'.");
            }
            System.out.println("If you would like to enroll in a class please enter 'enroll'.");
        }
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(this.inputStream);
        List<Course> courses = service.getCourses();

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("enroll")) {
                scanner.close();

                return input;
            } else if(input.equalsIgnoreCase("view")) {
                try {
                    System.out.println("Please enter an integer of the class list (for example press 1 for " +
                            courses.get(0) + (courses.size() > 1 ? ", 2 for " + courses.get(1) + ", etc)." : ""));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("It seems the list of courses is empty, please enroll in courses first.");
                }

                input = scanner.nextLine();
                try {
                    int index = Integer.parseInt(input);

                    try {

                        Course course = service.getCourses().get(index - 1);
                        System.out.println("You've selected: " + input + ". " + course.getName() + ". Is this correct? (y/n)");

                        input = scanner.nextLine();
                        if(input.equalsIgnoreCase("y")) {
                            scanner.close();
                            service.viewCourse(course);

                            return "detail";

                        } else if(!input.equalsIgnoreCase("n")) {
                            System.out.println("Please enter 'y' or 'n' for yes or no.");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("You entered an invalid number! Please check which the numbers in front of then classes!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("It seems you didn't enter an integer. Please enter an integer.");
                }
            }
        }
    }
}
