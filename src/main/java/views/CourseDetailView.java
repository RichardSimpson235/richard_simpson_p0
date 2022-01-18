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
import java.util.Scanner;

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

    @Override
    public void render() {
        System.out.println("Detailed Class Viewer");
        renderCourse(this.course);
    }

    public void renderCourse(Course course) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("==================================");
        System.out.println("Name: " + course.getName());
        System.out.println("Description: " + course.getDescription());
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentStartDate())));
        System.out.println("Enrollment Start Date: " + dateFormat.format(new Date(course.getEnrollmentEndDate())));
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
        System.out.println("==================================");
    }

    @Override
    public String listen() {

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

        while(true) {
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {

                return input;
            } else if(input.equalsIgnoreCase("unenroll")) {
                try {
                    accountService.unenroll(this.course);
                } catch (EnrollmentFailedException e) {
                    System.out.println("Sorry, we were not able to unenroll you from that class.");
                    System.out.println();
                }

                return "student";
            } else if(input.equalsIgnoreCase("enroll")) {
                try {
                    accountService.enroll(this.course);
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
                } catch (DeletionFailedException e) {
                    System.out.println("It seems we were unable to delete that course.");
                }
                System.out.println("Course: " + course.getName() + " was deleted!");

                return "faculty";
            } else if(this.course.isValidField(input)) {
                System.out.println("What would you like to change it to?");
                String newFieldData = scanner.nextLine();

                try {
                    courseService.editCourse(input, newFieldData);

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
