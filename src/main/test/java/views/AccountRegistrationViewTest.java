package main.test.java.views;

import main.java.views.AccountRegistrationView;
import main.test.java.mocks.MockRegistrationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

public class AccountRegistrationViewTest {

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        MockRegistrationService mockService = new MockRegistrationService();
        AccountRegistrationView view = new AccountRegistrationView(input, mockService);
        view.render();
        assert true;
    }

    @Test
    public void testStringInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("richard\nsimpson\n29\nusername\npassword".getBytes());
        MockRegistrationService mockService = new MockRegistrationService();
        AccountRegistrationView view = new AccountRegistrationView(input, mockService);

        String output = view.listen();
        assertEquals(output, "student");
    }

    @Test
    public void testExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("richard\nsimpson\nexit\nusername\npassword".getBytes());
        MockRegistrationService mockService = new MockRegistrationService();
        AccountRegistrationView view = new AccountRegistrationView(input, mockService);

        String output = view.listen();
        assertEquals(output, "exit");
    }
}
