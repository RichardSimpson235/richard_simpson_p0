package main.test.java.views;

import main.java.views.StudentView;
import main.test.java.mocks.services.AccountServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentViewTest {

    private AccountServiceMock mockService;

    @BeforeEach
    public void init() {
        mockService = new AccountServiceMock();
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        StudentView view = new StudentView(input, mockService);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        StudentView view = new StudentView(input, mockService);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testListenWithEnroll() {
        ByteArrayInputStream input = new ByteArrayInputStream("enroll".getBytes());
        StudentView view = new StudentView(input, mockService);

        assertEquals(view.listen(), "enroll");
    }

    @Test
    public void testListenWithViewAndValidClass() {
        ByteArrayInputStream input = new ByteArrayInputStream("view\n1\ny".getBytes());
        StudentView view = new StudentView(input, mockService);

        assertEquals(view.listen(), "detail");
    }
}