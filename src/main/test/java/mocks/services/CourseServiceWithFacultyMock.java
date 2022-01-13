package main.test.java.mocks.services;

import main.java.exceptions.InvalidDataException;
import main.java.models.Course;
import main.java.services.CourseService;
import main.java.structures.List;
import main.test.java.mocks.models.CourseMock;

public class CourseServiceWithFacultyMock extends CourseService {

    public CourseServiceWithFacultyMock() {
        super(null, null);
    }

    @Override
    public void deleteCourse() {
    }

    @Override
    public Course getCourse() {
        return new CourseMock();
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new List<>();
        list.add(new CourseMock());

        return list;
    }

    @Override
    public void editCourse(String fieldName, String fieldData) throws InvalidDataException {
    }

    @Override
    public boolean isUserFaculty() {
        return true;
    }
}
