package main.test.java.mocks;

import main.java.exceptions.AuthenticationException;
import main.java.services.LoginService;

public class MockLoginService extends LoginService {

    @Override
    public String authenticate(String username, String password) throws AuthenticationException {

        if(username.equalsIgnoreCase("student") || username.equalsIgnoreCase("faculty")) {
            return username;
        } else {
            throw new AuthenticationException("bad username");
        }
    }
}
