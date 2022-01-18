package main.java.views;

import main.java.exceptions.CourseRetrievalException;
import main.java.structures.List;
import main.java.models.Course;
import main.java.services.CourseService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseRegistryView extends AbstractView {

    private final CourseService service;
    private List<Course> courses;

    public CourseRegistryView(InputStream inputStream, CourseService service) {
        super(inputStream);
        this.service = service;
    }

    @Override
    public void render() {
        System.out.println("====================================================================");
        System.out.println("========================  COURSE REGISTRY  =========================");
        System.out.println("====================================================================");
        System.out.println("The following is a list of all of the classes:");

        try {
            this.courses = service.getAllCourses();
            for(int i = 1; i <= courses.size(); i++) {
                renderCourse(this.courses.get(i - 1), i);
            }
            System.out.println("====================================================================");

            System.out.println("Please enter the integer of its spot in the list (ex '1') to view the course detail page.");
            System.out.println("You may enter 'home' to return to your home page.");
        } catch (CourseRetrievalException | IndexOutOfBoundsException e) {
            System.out.println("The registry seems to be having some problems, try again later. To exit type, 'exit'.");
        }
    }

    private void renderCourse(Course course, int index) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("====================================================================");
        System.out.println("===============================  " + index + "  ================================");
        System.out.println("Name: " + course.getName());
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentStartDate())));
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentEndDate())));
        System.out.println("Credits: " + course.getCredits());
    }

    @Override
    public String listen() {

        while(true) {
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("exit")) {
                return input;
            } else if(input.equalsIgnoreCase("home")) {
                return "student";
            } else {
                int index = Integer.parseInt(input);
                try {
                    Course course = this.courses.get(index - 1);
                    System.out.println("You've selected: " +  input + " " + course.getName() + ", is this correct? (y/n)");
                    input = scanner.nextLine();
                    if(input.equalsIgnoreCase("y")) {
                        service.selectCourse(course);

                        return "detail";
                    } else if(!input.equalsIgnoreCase("n")) {
                        System.out.println("Please enter 'y' or 'n' for yes or no.");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("We got an invalid integer, please check that you entered the correct integer!");
                }
            }
        }
    }
}
