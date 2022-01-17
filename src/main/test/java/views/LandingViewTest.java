package main.test.java.views;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.views.LandingView;

import java.io.ByteArrayInputStream;

public class LandingViewTest {

    @Test
    @Disabled
    public void testRender() {
        ByteArrayInputStream input = new ByteArrayInputStream("login".getBytes());
        LandingView view = new LandingView(input);
        view.render();
        assert true;
    }

    @Test
    public void testListenLogin() {
        ByteArrayInputStream input = new ByteArrayInputStream("login".getBytes());
        LandingView view = new LandingView(input);

        assertEquals(view.listen(), "login");
    }

    @Test
    public void testListenRegister() {
        ByteArrayInputStream input = new ByteArrayInputStream("register".getBytes());
        LandingView view = new LandingView(input);

        assertEquals(view.listen(), "register");
    }

    @Test
    public void testListenExit() {
        ByteArrayInputStream input = new ByteArrayInputStream("exit".getBytes());
        LandingView view = new LandingView(input);

        assertEquals(view.listen(), "exit");
    }

    @Test
    public void testListenInvalid() {
        ByteArrayInputStream input = new ByteArrayInputStream("zxcvnajksdf\nexit".getBytes());
        LandingView view = new LandingView(input);

        assertEquals(view.listen(), "exit");
    }
}
