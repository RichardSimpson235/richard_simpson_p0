package main.java.views;

import main.java.models.Faculty;
import main.java.models.Student;
import main.java.services.CourseService;
import main.java.structures.List;
import main.java.models.Course;
import main.java.services.AccountService;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentView extends AbstractView {

    private final AccountService accountService;
    private final CourseService courseService;
    private List<Course> courses;

    public StudentView(InputStream inputStream, AccountService accountService, CourseService courseService) {
        super(inputStream);
        this.accountService = accountService;
        this.courseService = courseService;
    }

    /**
     * This method prints out the screen for the user to read.
     */
    @Override
    public void render() {
        System.out.println("====================================================================");
        this.courses = accountService.getCourses();

        renderUser();

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
        System.out.println("If you would like to view the registry of all classes please type 'registry'.");

    }

    /**
     * This method renders the user object to the screen
     */
    private void renderUser() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Student student = (Student) accountService.getUser();
        System.out.println("Welcome " + student.getFirstName() + " " + student.getLastName() + "!");
        System.out.println("====================================================================");
        System.out.println("First Name: " + student.getFirstName());
        System.out.println("Last Name: " + student.getLastName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Date of Birth: " + dateFormat.format(student.getDateOfBirth()));
        System.out.println("Meal Plan  Tier: " + student.getMealPlanTier());
        System.out.println("Major: " + student.getMajor());
        System.out.println("====================================================================");
    }

    /**
     * This method renders a course object to the screen
     *
     * @param course the course to render
     */
    private void renderCourse(Course course, int index) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("====================================================================");
        System.out.println("===============================  " + index + "  ================================");
        System.out.println("Name: " + course.getName());
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentStartDate())));
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentEndDate())));
        System.out.println("Credits: " + course.getCredits());
        System.out.println("Instructor: " + course.getProfessor().getFirstName() + " " + course.getProfessor().getLastName());
    }

    /**
     * This method listens for user input. The user can enter 'exit' at any time to quit the application.
     * An example of how this page is used is as follows (A stands for the application, U for the user):
     * =========================================================================================================
     * (A)-> You are enrolled in the following courses:
     * (course list)
     * (A)-> If you would like to see the details of a class please enter 'view'.
     * (A)-> If you would like to view the registry of all classes please type 'registry'.
     * (U)-> view
     * (A)-> Please enter an integer of the class list (for example press 1 for Linear Algebra)
     * (U)-> 1
     * (navigates to course detail view)
     * =========================================================================================================
     *
     * @return the next view to navigate to
     */
    @Override
    public String listen() {
        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("registry")) {

                return input;
            } else if(input.equalsIgnoreCase("view")) {
                try {
                    System.out.println("Please enter an integer of the class list (for example press 1 for " +
                            courses.get(0).getName() + (courses.size() > 1 ? ", 2 for " + courses.get(1).getName() + ", etc)." : "") + ")");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("It seems the list of courses is empty, please enroll in courses first.");
                }

                input = scanner.nextLine();
                if(input.equalsIgnoreCase("exit")) {
                    return input;
                }
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
            } else {
                System.out.println("It seems you entered an unrecognized command, please try again.");
            }
        }
    }
}
