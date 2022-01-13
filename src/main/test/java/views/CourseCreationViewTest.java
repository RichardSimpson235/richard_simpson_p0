package main.test.java.views;

import main.java.views.CourseCreationView;
import main.test.java.mocks.services.CourseServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseCreationViewTest {

    private CourseServiceMock courseServiceMock;

    @BeforeEach
    public void init() {
        this.courseServiceMock = new CourseServiceMock();
    }

    @Test
//    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        CourseCreationView view = new CourseCreationView(input, courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        CourseCreationView view = new CourseCreationView(input, courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testValidInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("Linear Algebra\nMath\n01/13/2022\n01/14/2022".getBytes());
        CourseCreationView view = new CourseCreationView(input, courseServiceMock);

        assertEquals(view.listen(), "faculty");
    }
}
