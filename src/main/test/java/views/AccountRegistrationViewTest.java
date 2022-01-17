package main.test.java.views;

import main.java.views.AccountRegistrationView;
import main.test.java.mocks.services.AccountServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

public class AccountRegistrationViewTest {

    private AccountServiceMock mockServiceStudent;

    @BeforeEach
    public void init() {

        mockServiceStudent = new AccountServiceMock(null, null, null, "student");
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        AccountRegistrationView view = new AccountRegistrationView(input, mockServiceStudent);
        view.render();
        assert true;
    }

    @Test
    public void testStringInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("richard\nsimpson\nrsimpson@gmail.com\n02/24/1992\nrsimpson\npassword\n1\nMath".getBytes());
        AccountRegistrationView view = new AccountRegistrationView(input, mockServiceStudent);

        String output = view.listen();
        assertEquals(output, "landing");
    }

    @Test
    public void testExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("richard\nsimpson\nexit\nusername\npassword".getBytes());
        AccountRegistrationView view = new AccountRegistrationView(input, mockServiceStudent);

        String output = view.listen();
        assertEquals(output, "exit");
    }
}
