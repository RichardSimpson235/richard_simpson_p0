package main.test.java.views;

import main.java.views.FacultyView;
import main.test.java.mocks.services.AccountServiceMock;
import main.test.java.mocks.services.CourseServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacultyViewTest {

    private AccountServiceMock accountServiceMock;
    private CourseServiceMock courseServiceMock;

    @BeforeEach
    public void init() {
        this.accountServiceMock = new AccountServiceMock(null, null, null, "faculty");
        this.courseServiceMock = new CourseServiceMock(null);
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        FacultyView view = new FacultyView(input, this.accountServiceMock, this.courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        FacultyView view = new FacultyView(input, this.accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testListenWithNew() {
        ByteArrayInputStream input = new ByteArrayInputStream("new".getBytes());
        FacultyView view = new FacultyView(input, this.accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "new");
    }

    @Test
    public void testListenWithEdit() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\ny".getBytes());
        FacultyView view = new FacultyView(input, this.accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "detail");
    }
}
