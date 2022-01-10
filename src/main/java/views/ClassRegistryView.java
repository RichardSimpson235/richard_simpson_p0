package main.java.views;

import main.java.collections.List;
import main.java.models.Course;
import main.java.models.Student;
import main.java.services.EnrollmentService;

import java.io.InputStream;
import java.util.Scanner;

public class ClassRegistryView extends AbstractView {

    private final EnrollmentService service;
    private final List<Integer> validSelections;
    private final Student student;

    public ClassRegistryView(InputStream inputStream, EnrollmentService service, Student student) {
        super(inputStream);
        this.service = service;
        this.student = student;
        this.validSelections = new List<>();
    }

    @Override
    public void render() {
        System.out.println("The following is a list of all of the classes:");

        List<Course> courses = service.getCourses();
        for(int i = 1; i <= courses.size(); i++) {
            System.out.println(courses.get(i));
            validSelections.add(i);
        }

        System.out.println("Please enter the integer of its spot in the list (ex '1')");
    }

    @Override
    protected String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("exit")) {
                return input;
            } else {
                if(this.validSelections.contains(Integer.parseInt(input))) {
                    service.enroll(Integer.parseInt(input), this.student);
                } else {
                    System.out.println("We got an invalid integer, please check that you entered the correct integer!");
                }
            }
        }
    }
}
