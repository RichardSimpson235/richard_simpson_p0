package main.test.java.views;

import main.java.views.CourseDetailView;
import main.test.java.mocks.services.CourseServiceMock;
import main.test.java.mocks.services.CourseServiceWithFacultyMock;
import main.test.java.mocks.services.CourseServiceWithStudentEnrolledTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDetailViewTest {

    private CourseServiceMock courseServiceMock;
    private CourseServiceWithFacultyMock courseServiceWithFacultyMock;
    private CourseServiceWithStudentEnrolledTest mockService;

    @BeforeEach
    public void init() {
        this.courseServiceMock = new CourseServiceMock();
        this.courseServiceWithFacultyMock = new CourseServiceWithFacultyMock();
        this.mockService = new CourseServiceWithStudentEnrolledTest();
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        CourseDetailView view = new CourseDetailView(input, courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        CourseDetailView view = new CourseDetailView(input, courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testFacultyEdit() {
        ByteArrayInputStream input = new ByteArrayInputStream("name\nalgebra".getBytes());
        CourseDetailView view = new CourseDetailView(input, courseServiceWithFacultyMock);

        assertEquals(view.listen(), "faculty");
    }

    @Test
    public void testDelete() {
        ByteArrayInputStream input = new ByteArrayInputStream("delete".getBytes());
        CourseDetailView view = new CourseDetailView(input, courseServiceWithFacultyMock);

        assertEquals(view.listen(), "faculty");
    }

    @Test
    public void testEnroll() {
        ByteArrayInputStream input = new ByteArrayInputStream("enroll".getBytes());
        CourseDetailView view = new CourseDetailView(input, courseServiceMock);

        assertEquals(view.listen(), "student");
    }

    @Test
    public void testUnenroll() {
        ByteArrayInputStream input = new ByteArrayInputStream("unenroll".getBytes());
        CourseDetailView view = new CourseDetailView(input, mockService);

        assertEquals(view.listen(), "student");
    }
}
