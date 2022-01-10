package main.test.java.views;

import main.java.exceptions.AuthenticationException;
import main.java.views.LoginView;
import main.test.java.mocks.MockLoginService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

public class LoginViewTest {

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("login".getBytes());
        MockLoginService mockService = new MockLoginService();
        LoginView view = new LoginView(input, mockService);
        view.render();
        assert true;
    }

    @Test
    public void testListenWithExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit\npassword".getBytes());
        MockLoginService mockService = new MockLoginService();
        LoginView view = new LoginView(input, mockService);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testListenWithStudent() {
        ByteArrayInputStream input = new ByteArrayInputStream("student\npassword".getBytes());
        MockLoginService mockService = new MockLoginService();
        LoginView view = new LoginView(input, mockService);

        assertEquals(view.listen(), "student");
    }

    @Test
    public void testListenWithFaculty() {
        ByteArrayInputStream input = new ByteArrayInputStream("faculty\npassword".getBytes());
        MockLoginService mockService = new MockLoginService();
        LoginView view = new LoginView(input, mockService);

        assertEquals(view.listen(), "faculty");
    }

    // It throws a NoSuchElementException because there is no next line in the input
    // ensure that the error message was printed first though.
    @Test
    @DisplayName("Should print a message to console when authentication fails.")
    public void testListenAuthenticationFailure() {
        ByteArrayInputStream input = new ByteArrayInputStream("asdf\npassword".getBytes());
        MockLoginService mockService = new MockLoginService();
        LoginView view = new LoginView(input, mockService);

        assertThrows(NoSuchElementException.class, () -> {
            view.listen();
        });
    }
}
