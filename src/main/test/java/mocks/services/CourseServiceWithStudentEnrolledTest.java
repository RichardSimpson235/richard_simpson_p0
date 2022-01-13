package main.test.java.mocks.services;

import main.java.exceptions.FailedEnrollmentException;
import main.java.models.Course;
import main.java.repositories.CourseRepository;
import main.java.services.ContextService;
import main.java.services.CourseService;
import main.java.structures.List;
import main.test.java.mocks.models.CourseMock;

public class CourseServiceWithStudentEnrolledTest extends CourseService {

    public CourseServiceWithStudentEnrolledTest() {
        super(null, null);
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new List<>();
        list.add(new CourseMock());

        return list;
    }

    @Override
    public Course getCourse() {
        return new CourseMock();
    }

    @Override
    public void selectCourse(int index) {
    }

    @Override
    public void createCourse(String courseName, String description, long enrollmentPeriodStart, long enrollmentPeriodEnd) {
    }

    @Override
    public void unenrollUser() throws FailedEnrollmentException {
    }

    @Override
    public boolean isUserEnrolled() {
        return true;
    }

    @Override
    public boolean isUserFaculty() {
        return false;
    }
}
