package main.java.views;

import jdk.internal.util.xml.impl.Input;
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
            System.out.println("You have not created any classes yet.");
        } else {
            System.out.println("You have created the following classes:");
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
    protected String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                scanner.close();
                return input;
            }

            if (!validSelections.contains(Integer.parseInt(input))) {
                System.out.println("You entered an invalid number! We got: " + input + ", we need one of: " + validSelections);
            } else {
                scanner.close();
                return input;
            }

        }
    }
}
