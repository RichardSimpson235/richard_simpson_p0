package main.java.views;

import main.java.services.CourseService;
import main.java.structures.List;
import main.java.models.Course;
import main.java.services.AccountService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class StudentView extends AbstractView {

    private final AccountService accountService;
    private final CourseService courseService;
    private List<Course> courses;

    public StudentView(InputStream inputStream, AccountService accountService, CourseService courseService) {
        super(inputStream);
        this.accountService = accountService;
        this.courseService = courseService;
    }

    @Override
    public void render() {
        System.out.println("====================================================================");
        this.courses = accountService.getCourses();

        System.out.println("Welcome " + accountService.getUserName() + "!");

        if(this.courses.isEmpty()) {
            System.out.println("You have not enrolled in any courses yet.");
            System.out.println("====================================================================");
        } else {
            System.out.println("You are enrolled in the following courses:");
            for(int i = 1; i <= this.courses.size(); i++) {
                renderCourse(this.courses.get(i - 1), i);
            }

            System.out.println("====================================================================");

            if (this.courses.size() != 0) {
                System.out.println("If you would like to see the details of a class please enter 'view'.");
            }
        }
        System.out.println("If you would like to enroll in a class please enter 'enroll'.");

    }

    private void renderCourse(Course course, int index) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("====================================================================");
        System.out.println("===============================  " + index + "  ================================");
        System.out.println("Name: " + course.getName());
        System.out.println("Description: " + course.getDescription());
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentStartDate())));
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentEndDate())));
        System.out.println("Credits: " + course.getCredits());
        System.out.println("Instructor: " + course.getProfessor().getFirstName() + " " + course.getProfessor().getLastName());
    }

    @Override
    public String listen() {
        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("enroll")) {

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

                        Course course = this.courses.get(index - 1);
                        System.out.println("You've selected: " + input + ". " + course.getName() + ". Is this correct? (y/n)");

                        input = scanner.nextLine();
                        if(input.equalsIgnoreCase("y")) {
                            courseService.selectCourse(course);

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
