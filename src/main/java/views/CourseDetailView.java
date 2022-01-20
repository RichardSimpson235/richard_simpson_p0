package main.java.views;

import main.java.exceptions.*;
import main.java.models.Course;
import main.java.models.Student;
import main.java.services.AccountService;
import main.java.services.CourseService;
import main.java.structures.List;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseDetailView extends AbstractView {

    private final AccountService accountService;
    private final CourseService courseService;
    private final Course course;

    public CourseDetailView(InputStream inputStream, AccountService accountService, CourseService courseService) {
        super(inputStream);
        this.accountService = accountService;
        this.courseService = courseService;

        this.course = courseService.getCourse();
    }

    /**
     * This method prints out the screen for the user to read.
     */
    @Override
    public void render() {
        renderCourse(this.course);
        System.out.println("You may enter 'home' to return to your home page.");
        if(this.accountService.isUserFaculty()) {
            System.out.println("If you would like to edit a field, enter the field's name (for example: 'name')." +
                    "For enrollment start or end, please enter 'start' or 'end' respectively.");
            System.out.println("If you would like to delete the class please enter 'delete'.");
        } else {
            if(this.course.isEnrolled((Student) this.accountService.getUser())) {
                System.out.println("If you would like to unenroll from this class type 'unenroll'.");
            } else {
                System.out.println("If you would like to enroll in this class, type 'enroll'");
            }
        }
    }

    /**
     * This method renders a course object to the screen
     *
     * @param course the course to render
     */
    private void renderCourse(Course course) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("====================================================================");
        renderNameHeader();
        System.out.println("====================================================================");
        renderWrappedDescription();
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentStartDate())));
        System.out.println("Enrollment End Date: " + dateFormat.format(new Date(course.getEnrollmentEndDate())));
        System.out.println("Credits: " + course.getCredits());

        if(this.accountService.isUserFaculty()) {
            // Construct list of students enrolled in the class
            List<Student> students = course.getStudents();
            StringBuilder output = new StringBuilder("Students: ");
            for (int i = 0; i < students.size(); i++) {
                output.append(students.get(i).getFirstName());
                output.append(" ");
                output.append(students.get(i).getLastName());
                output.append(i != students.size() - 1 ? ", " : "");
            }

            System.out.println(output);
        } else {
            // Then they're a faculty member, so we just need their name
            System.out.println("Instructor: " + course.getProfessor().getFirstName() + " " + course.getProfessor().getLastName());
        }
        System.out.println("====================================================================");
    }

    /**
     * This method renders the name of the course at the top of the section. For example:
     * ====================================================================
     * =========================== Course Name ============================
     * ====================================================================
     */
    private void renderNameHeader() {
        StringBuilder title = new StringBuilder();
        int spacerLength = (68 - course.getName().length() - 2) / 2;

        // Header is split into 3 parts, the first spacer, the title, and the second spacer:
        for(int i = 0; i < spacerLength; i++) {
            title.append("=");
        }

        title.append(" ");
        title.append(course.getName());
        title.append(" ");
        if(course.getName().length() % 2 == 1) {
            title.append(" ");
        }

        for(int i = 0; i < spacerLength; i++) {
            title.append("=");
        }

        System.out.println(title);
    }

    /**
     * This method wraps the description string of the course to fit the screen better.
     */
    private void renderWrappedDescription() {
        String s = "Description: " + course.getDescription();


        int numberOfLines = (int) Math.ceil((double) s.length() / 68);
        for (int i = 0; i < numberOfLines; i++) {
            int start = Math.min(i * 68, s.length() - 1);
            int end = Math.min((i + 1) * 68, s.length());;
            System.out.println(s.substring(start, end));
        }
    }

    /**
     * This method listens for user input. The user can enter 'exit' at any time to quit the application.
     * They can also enter 'home' at any time to return to their user page. An example of how this page is
     * used is as follows (A stands for the application, U for the user):
     * For a faculty member:
     * =========================================================================================================
     * (A)-> You may enter 'home' to return to your home page.
     * (A)-> If you would like to edit a field, enter the field's name (for example: 'name'). For enrollment
     * start or end, please enter 'start' or 'end' respectively.
     * (A)-> If you would like to delete the class please enter 'delete'.
     * (U)-> name
     * (A)-> What would you like to change it to?
     * (U)-> Abstract Algebra
     * (A)-> The course has been updated!
     * =========================================================================================================
     *
     * @return the next view to navigate to
     */
    @Override
    public String listen() {

        while(true) {
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {

                return input;
            } else if(input.equalsIgnoreCase("unenroll")) {
                try {
                    accountService.unenroll(this.course);
                    courseService.removeStudent((Student) accountService.getUser());
                    System.out.println("You have been unenrolled!");
                } catch (EnrollmentFailedException e) {
                    System.out.println("Sorry, we were not able to unenroll you from that class.");
                    System.out.println();
                } catch (EnrollmentRangeException e) {
                    System.out.println("You can't unenroll from a class outside of the enrollment period!");
                }

                return "student";
            } else if(input.equalsIgnoreCase("enroll")) {
                try {
                    accountService.enroll(this.course);
                    courseService.addStudent((Student) accountService.getUser());
                    System.out.println("You have been enrolled!");
                } catch (EnrollmentFailedException e) {
                    System.out.println("Sorry, we were not able to enroll you in that class.");
                    System.out.println();
                } catch (EnrollmentRangeException e) {
                    System.out.println("We're sorry but you cannot enroll in a class that is past its enrollment period end date.");
                    System.out.println();
                }

                return "student";
            } else if(input.equalsIgnoreCase("delete")) {
                try {
                    courseService.deleteCourse();
                    accountService.removeCourse(course);
                    System.out.println("Course: " + course.getName() + " was deleted!");
                } catch (DeletionFailedException e) {
                    System.out.println("It seems we were unable to delete that course.");
                }

                return "faculty";
            } else if(input.equalsIgnoreCase("home")) {
                if(this.accountService.isUserFaculty()) {
                    return "faculty";
                } else {
                    return "student";
                }
            } else if(this.course.isValidField(input)) {
                System.out.println("What would you like to change it to?");
                String newFieldData = scanner.nextLine();

                try {
                    courseService.editCourse(input, newFieldData);
                    System.out.println("The course has been updated!");

                    return "faculty";
                } catch(NumberFormatException e) {
                    System.out.println("It seems you inputted something that wasn't an integer for credits, make sure you enter an integer!");
                } catch (CourseModificationException e) {
                    System.out.println("We're sorry but we were not able to modify that course.");
                } catch (ParseException e) {
                    System.out.println("It seems the date you entered was improperly formatted, please try again.");
                } catch (InvalidFieldException e) {
                    System.out.println("It seems you got the name of a field wrong, please try again.");
                }
            } else {
                System.out.println("You entered an invalid option. We got: " + input + ", please check the valid inputs" +
                        " again.");
            }
        }
    }
}
