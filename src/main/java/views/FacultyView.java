package main.java.views;

import main.java.collections.List;
import main.java.services.FacultyService;
import main.java.models.Course;

import java.io.InputStream;
import java.util.Scanner;

public class FacultyView extends AbstractView {

    private final FacultyService service;
    private final List<Integer> validSelections;

    public FacultyView(InputStream inputStream, FacultyService service) {
        super(inputStream);
        this.service = service;

        validSelections = new List<>();
    }

    @Override
    public void render() {
        System.out.println("==========================================");
         List<Course> courses = service.getCourses();

        System.out.println("Welcome " + service.getFacultyName() + "!");
        if(courses.isEmpty()) {
            System.out.println("You have not created any courses yet.");
        } else {
            System.out.println("You have created the following courses:");
            for(int i = 1; i <=  courses.size(); i++) {
                System.out.println(i + ". " + courses.get(i - 1));
                validSelections.add(i);
            }

            System.out.println("If you would like to edit one of your classes, please enter an integer in the list " +
                                "(for example press 1 for " +
                                courses.get(0) + (courses.size() > 1 ? ", 2 for " + courses.get(1) + ", etc)." : ""));
        }

        System.out.println("If you would like to add a new class, please enter 'new'.");
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                scanner.close();
                return input;
            }

            try {
                int index = Integer.parseInt(input);

                if(!validSelections.contains((index))) {
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
