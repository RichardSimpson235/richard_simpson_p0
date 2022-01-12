package main.java.views;

import main.java.structures.List;
import main.java.models.Course;
import main.java.services.CourseService;

import java.io.InputStream;
import java.util.Scanner;

public class CourseRegistryView extends AbstractView {

    private final CourseService service;
    private final List<Integer> validSelections;

    public CourseRegistryView(InputStream inputStream, CourseService service) {
        super(inputStream);
        this.service = service;
        this.validSelections = new List<>();
    }

    @Override
    public void render() {
        System.out.println("The following is a list of all of the classes:");

        List<Course> courses = service.getAllCourses();
        for(int i = 1; i <= courses.size(); i++) {
            System.out.println(courses.get(i));
            validSelections.add(i);
        }

        System.out.println("Please enter the integer of its spot in the list (ex '1')");
    }

    @Override
    public String listen() {
        Scanner scanner = new Scanner(this.inputStream);

        while(true) {
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("exit")) {
                scanner.close();
                return input;
            } else {
                int index = Integer.parseInt(input);
                if(this.validSelections.contains(index)) {
                    service.selectCourse(index);
                    scanner.close();

                    return "detail";
                } else {
                    System.out.println("We got an invalid integer, please check that you entered the correct integer!");
                }
            }
        }
    }
}
