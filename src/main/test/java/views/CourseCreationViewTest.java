package main.test.java.views;

import main.java.services.AccountService;
import main.java.views.CourseCreationView;
import main.test.java.mocks.services.AccountServiceMock;
import main.test.java.mocks.services.CourseServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseCreationViewTest {

    private CourseServiceMock courseServiceMock;
    private AccountServiceMock accountServiceMock;

    @BeforeEach
    public void init() {
        this.accountServiceMock = new AccountServiceMock(null, null, null, "faculty");
        this.courseServiceMock = new CourseServiceMock(null);
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        CourseCreationView view = new CourseCreationView(input, this.accountServiceMock, this.courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        CourseCreationView view = new CourseCreationView(input, this.accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testValidInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("Linear Algebra\ndescription\n01/13/2022\n01/14/2022\n3".getBytes());
        CourseCreationView view = new CourseCreationView(input, this.accountServiceMock, this.courseServiceMock);

        assertEquals(view.listen(), "faculty");
    }
}
