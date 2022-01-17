package main.test.java.views;

import main.java.views.CourseRegistryView;
import main.test.java.mocks.services.CourseServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseRegistryViewTest {

    private CourseServiceMock courseServiceMock;

    @BeforeEach
    public void init() {
        this.courseServiceMock = new CourseServiceMock(null);
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        CourseRegistryView view = new CourseRegistryView(input, courseServiceMock);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        CourseRegistryView view = new CourseRegistryView(input, courseServiceMock);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testSelectCourse() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\ny".getBytes());
        CourseRegistryView view = new CourseRegistryView(input, courseServiceMock);

        // have to call render to populate course list
        view.render();
        assertEquals(view.listen(), "detail");
    }
}
