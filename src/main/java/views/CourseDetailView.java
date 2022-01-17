package main.java.views;

import main.java.exceptions.*;
import main.java.models.Course;
import main.java.models.Student;
import main.java.services.AccountService;
import main.java.services.CourseService;

import java.io.InputStream;
import java.text.ParseException;
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

    public void renderCourse(Course course) {}

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
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {
                scanner.close();

                return input;
            } else if(input.equalsIgnoreCase("unenroll")) {
                try {
                    accountService.unenroll(this.course);
                } catch (EnrollmentFailedException e) {
                    System.out.println("Sorry, we were not able to unenroll you from that class.");
                }
                scanner.close();

                return "student";
            } else if(input.equalsIgnoreCase("enroll")) {
                try {
                    accountService.enroll(this.course);
                } catch (EnrollmentFailedException e) {
                    System.out.println("Sorry, we were not able to enroll you in that class.");
                }
                scanner.close();

                return "student";
            } else if(input.equalsIgnoreCase("delete")) {
                try {
                    courseService.deleteCourse();
                } catch (DeletionFailedException e) {
                    System.out.println("It seems we were unable to delete that course.");
                }
                System.out.println("Course: " + course.getName() + " was deleted!");
                scanner.close();

                return "faculty";
            } else if(this.course.isValidField(input)) {
                System.out.println("What would you like to change it to?");
                String newFieldData = scanner.nextLine();

                try {
                    courseService.editCourse(input, newFieldData);
                    scanner.close();

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
