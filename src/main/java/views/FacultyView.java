package main.java.views;

import main.java.structures.List;
import main.java.services.AccountService;
import main.java.models.Course;

import java.io.InputStream;
import java.util.Scanner;

public class FacultyView extends AbstractView {

    private final AccountService service;

    public FacultyView(InputStream inputStream, AccountService service) {
        super(inputStream);
        this.service = service;
    }

    @Override
    public void render() {
        System.out.println("==========================================");
         List<Course> courses = service.getCourses();

        System.out.println("Welcome " + service.getAccountName() + "!");
        if(courses.isEmpty()) {
            System.out.println("You have not created any courses yet.");
        } else {
            System.out.println("You have created the following courses:");
            for(int i = 1; i <=  courses.size(); i++) {
                System.out.println(i + ". " + courses.get(i - 1));
            }

            if(courses.size() != 0) {
                System.out.println("If you would like to edit one of your classes, please enter an integer in the list " +
                        "(for example press 1 for " +
                        courses.get(0) + (courses.size() > 1 ? ", 2 for " + courses.get(1) + ", etc)." : ")"));
            }
        }

        System.out.println("If you would like to add a new class, please enter 'new'.");
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("new")) {
                scanner.close();
                return input;
            }

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
                    System.out.println("You entered an invalid number! Please check the integers in front of the classes.");
                }
            } catch (NumberFormatException e) {
                System.out.println("It seems you didn't enter an integer. Please enter an integer.");
            }
        }
    }
}
