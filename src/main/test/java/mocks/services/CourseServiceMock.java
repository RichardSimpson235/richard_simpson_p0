package main.test.java.mocks.services;

import main.java.models.Course;
import main.java.repositories.CourseRepository;
import main.java.services.ContextService;
import main.java.services.CourseService;
import main.java.structures.List;
import main.test.java.mocks.models.CourseMock;

public class CourseServiceMock extends CourseService {

    public CourseServiceMock() {
        super(null, null);
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new List<>();
        list.add(new CourseMock());

        return list;
    }

    @Override
    public void selectCourse(int index) {
    }
}
