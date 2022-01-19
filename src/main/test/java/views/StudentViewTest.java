package main.test.java.views;

import main.java.views.StudentView;
import main.test.java.mocks.services.AccountServiceMock;
import main.test.java.mocks.services.CourseServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentViewTest {

    private AccountServiceMock accountServiceMock;
    private CourseServiceMock courseServiceMock;

    @BeforeEach
    public void init() {
        this.accountServiceMock = new AccountServiceMock(null, null, null, "student");
        this.courseServiceMock = new CourseServiceMock(null);
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        StudentView view = new StudentView(input, accountServiceMock, this.courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        StudentView view = new StudentView(input, accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testListenWithRegistry() {
        ByteArrayInputStream input = new ByteArrayInputStream("registry".getBytes());
        StudentView view = new StudentView(input, accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "registry");
    }

    @Test
    public void testListenWithViewAndValidClass() {
        ByteArrayInputStream input = new ByteArrayInputStream("view\n1\ny".getBytes());
        StudentView view = new StudentView(input, accountServiceMock, this.courseServiceMock);

        // need to call render to populate course list
        view.render();
        assertEquals(view.listen(), "detail");
    }
}