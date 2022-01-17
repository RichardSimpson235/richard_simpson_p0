package main.test.java.views;

import main.java.views.CourseDetailView;
import main.test.java.mocks.services.AccountServiceMock;
import main.test.java.mocks.services.CourseServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDetailViewTest {

//    private CourseServiceMock courseServiceMock;
//    private CourseServiceWithFacultyMock courseServiceWithFacultyMock;
//    private CourseServiceWithStudentEnrolledTest mockService;

    private AccountServiceMock accountServiceMockFaculty;
    private AccountServiceMock accountServiceMockStudent;
    private CourseServiceMock courseServiceMock;

    @BeforeEach
    public void init() {
        this.accountServiceMockFaculty = new AccountServiceMock(null, null, null, "faculty");
        this.accountServiceMockStudent = new AccountServiceMock(null, null, null, "student");
        this.courseServiceMock = new CourseServiceMock(null);
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        CourseDetailView view = new CourseDetailView(input, this.accountServiceMockFaculty, this.courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        CourseDetailView view = new CourseDetailView(input, this.accountServiceMockFaculty, this.courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testFacultyEdit() {
        ByteArrayInputStream input = new ByteArrayInputStream("name\nalgebra".getBytes());
        CourseDetailView view = new CourseDetailView(input, this.accountServiceMockFaculty, this.courseServiceMock);

        assertEquals(view.listen(), "faculty");
    }

    @Test
    public void testDelete() {
        ByteArrayInputStream input = new ByteArrayInputStream("delete".getBytes());
        CourseDetailView view = new CourseDetailView(input, this.accountServiceMockFaculty, this.courseServiceMock);

        assertEquals(view.listen(), "faculty");
    }

    @Test
    public void testEnroll() {
        ByteArrayInputStream input = new ByteArrayInputStream("enroll".getBytes());
        CourseDetailView view = new CourseDetailView(input, this.accountServiceMockStudent, this.courseServiceMock);

        assertEquals(view.listen(), "student");
    }

    @Test
    public void testUnenroll() {
        ByteArrayInputStream input = new ByteArrayInputStream("unenroll".getBytes());
        CourseDetailView view = new CourseDetailView(input, this.accountServiceMockStudent, this.courseServiceMock);

        assertEquals(view.listen(), "student");
    }
}
