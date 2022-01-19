package main.java.views;

import main.java.models.Faculty;
import main.java.models.Student;
import main.java.services.CourseService;
import main.java.structures.List;
import main.java.services.AccountService;
import main.java.models.Course;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacultyView extends AbstractView {

    private final AccountService accountService;
    private final CourseService courseService;
    private List<Course> courses;

    public FacultyView(InputStream inputStream, AccountService accountService, CourseService courseService) {
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

        System.out.println("Welcome " + accountService.getUserName() + "!");
        renderUser();
        if(this.courses.isEmpty()) {
            System.out.println("You have not created any courses yet.");
        } else {
            System.out.println("You have created the following courses:");
            for(int i = 1; i <=  this.courses.size(); i++) {
                renderCourse(this.courses.get(i - 1), i);
            }
            System.out.println("====================================================================");

            if(this.courses.size() != 0) {
                System.out.println("If you would like to edit one of your classes, please enter an integer in the list " +
                        "(for example press 1 for " +
                        this.courses.get(0).getName() + (this.courses.size() > 1 ? ", 2 for " + this.courses.get(1).getName() + ", etc)." : ")"));
            }
        }

        System.out.println("If you would like to add a new class, please enter 'new'.");
    }

    /**
     * This method renders the user object to the screen
     */
    private void renderUser() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("====================================================================");
        Faculty faculty = (Faculty) accountService.getUser();
        System.out.println("First Name: " + faculty.getFirstName());
        System.out.println("Last Name: " + faculty.getLastName());
        System.out.println("Email: " + faculty.getEmail());
        System.out.println("Date of Birth: " + dateFormat.format(faculty.getDateOfBirth()));
        System.out.println("Salary: " + faculty.getSalary());
        System.out.println("Department: " + faculty.getDepartment());
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

        List<Student> students = course.getStudents();
        StringBuilder output = new StringBuilder("Students: ");
        for (int i = 0; i < students.size(); i++) {
            output.append(students.get(i).getFirstName());
            output.append(" ");
            output.append(students.get(i).getLastName());
            output.append(i != students.size() - 1 ? ", " : "");
        }

        System.out.println(output);
    }

    /**
     * This method listens for user input. The user can enter 'exit' at any time to quit the application.
     * An example of how this page is used is as follows (A stands for the application, U for the user):
     * =========================================================================================================
     * (A)-> You have created the following courses:
     * (course list)
     * (A)-> If you would like to edit one of your classes, please enter an integer in the list (for example
     * press 1 for Linear Algebra).
     * (A)-> If you would like to add a new class, please enter 'new'.
     * (U)-> new
     * (navigates to course creation)
     * =========================================================================================================
     *
     * @return the next view to navigate to
     */
    @Override
    public String listen() {

        while(true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("new")) {
                return input;
            }

            try {
                int index = Integer.parseInt(input);

                try {
                    Course course = accountService.getCourses().get(index - 1);
                    System.out.println("You've selected: " + input + ". " + course.getName() + ". Is this correct? (y/n)");

                    input = scanner.nextLine();
                    if(input.equalsIgnoreCase("y")) {
                        courseService.selectCourse(course);

                        return "detail";
                    } else if(!input.equalsIgnoreCase("n")) {
                        System.out.println("Please enter 'y' or 'n' for yes or no.");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("You entered an invalid number! Please check the integers ot top of the classes.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That isn't a recognized command. Please enter an integer, 'new', or 'exit'.");
            }
        }
    }
}
