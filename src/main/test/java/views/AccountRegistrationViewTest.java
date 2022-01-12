package main.test.java.views;

import main.java.views.AccountRegistrationView;
import main.test.java.mocks.services.AccountServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

public class AccountRegistrationViewTest {

    private AccountServiceMock mockService;

    @BeforeEach
    public void init() {
        mockService = new AccountServiceMock();
    }

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        AccountRegistrationView view = new AccountRegistrationView(input, mockService);
        view.render();
        assert true;
    }

    @Test
    public void testStringInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("richard\nsimpson\n29\nusername\npassword".getBytes());
        AccountRegistrationView view = new AccountRegistrationView(input, mockService);

        String output = view.listen();
        assertEquals(output, "landing");
    }

    @Test
    public void testExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("richard\nsimpson\nexit\nusername\npassword".getBytes());
        AccountRegistrationView view = new AccountRegistrationView(input, mockService);

        String output = view.listen();
        assertEquals(output, "exit");
    }
}
