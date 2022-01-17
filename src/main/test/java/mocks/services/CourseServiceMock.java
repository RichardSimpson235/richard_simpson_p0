package main.test.java.mocks.services;

import main.java.exceptions.*;
import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.repositories.CourseRepository;
import main.java.services.CourseService;
import main.java.structures.List;

import java.text.ParseException;

public class CourseServiceMock extends CourseService {

    public CourseServiceMock(CourseRepository courseRepository) {
        super(courseRepository);
    }

    @Override
    public void createCourse(String name, String description, long enrollmentStart, long enrollmentEnd, int credits) throws CreationFailedException {
    }

    @Override
    public void deleteCourse() throws DeletionFailedException {
    }

    @Override
    public void editCourse(String fieldName, String fieldData) throws ParseException, InvalidFieldException, CourseModificationException, NumberFormatException {
    }

    @Override
    public void selectCourse(Course course) {
    }

    @Override
    public Course getCourse() {
        Faculty faculty = new Faculty();
        faculty.setUserId(1);
        faculty.setFirstName("first");
        faculty.setLastName("last");
        faculty.setEmail("first@gmail.com");
        faculty.setDateOfBirth(0L);
        faculty.setUsername("username");
        faculty.setPassword("password");
        faculty.setSalary(10);
        faculty.setDepartment("DEPT");

        Student student = new Student();
        student.setUserId(3);
        student.setFirstName("first");
        student.setLastName("last");
        student.setEmail("first@gmail.com");
        student.setDateOfBirth(0L);
        student.setUsername("username");
        student.setPassword("password");
        student.setMealPlanTier(10);
        student.setMajor("DEPT");

        Course course = new Course();
        course.setCourseId(1);
        course.setName("course1");
        course.setCourseId(1);
        course.setProfessor(faculty);
        course.addStudent(student);

        return course;
    }

    @Override
    public List<Course> getAllCourses() throws CourseRetrievalException {
        List<Course> courses = new List<>();
        courses.add(getCourse());

        return courses;
    }
}
