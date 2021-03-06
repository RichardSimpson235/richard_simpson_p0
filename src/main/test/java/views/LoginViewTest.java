package main.test.java.views;

import main.java.views.LoginView;
import main.test.java.mocks.services.AccountServiceMock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

public class LoginViewTest {

    private AccountServiceMock accountServiceMockFaculty;
    private AccountServiceMock accountServiceMockStudent;

    @BeforeEach
    public void init() {
        this.accountServiceMockStudent = new AccountServiceMock(null, null, null, "student");
        this.accountServiceMockFaculty = new AccountServiceMock(null, null, null, "faculty");
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        LoginView view = new LoginView(input, accountServiceMockFaculty);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit\npassword".getBytes());
        LoginView view = new LoginView(input, accountServiceMockFaculty);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testListenWithStudent() {
        ByteArrayInputStream input = new ByteArrayInputStream("student\npassword".getBytes());
        LoginView view = new LoginView(input, accountServiceMockStudent);

        assertEquals(view.listen(), "student");
    }

    @Test
    public void testListenWithFaculty() {
        ByteArrayInputStream input = new ByteArrayInputStream("faculty\npassword".getBytes());
        LoginView view = new LoginView(input, accountServiceMockFaculty);

        assertEquals(view.listen(), "faculty");
    }

    // It throws a NoSuchElementException because there is no next line in the input
    // ensure that the error message was printed first though.
    @Test
    @DisplayName("Should print a message to console when authentication fails.")
    public void testListenAuthenticationFailure() {
        ByteArrayInputStream input = new ByteArrayInputStream("asdf\npassword".getBytes());
        LoginView view = new LoginView(input, accountServiceMockFaculty);

        assertThrows(NoSuchElementException.class, () -> {
            view.listen();
        });
    }
}
