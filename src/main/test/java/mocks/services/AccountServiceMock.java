package main.test.java.mocks.services;

import main.java.exceptions.AuthenticationFailedException;
import main.java.exceptions.RegistrationFailedException;
import main.java.models.Course;
import main.java.services.AccountService;
import main.java.structures.List;

public class AccountServiceMock extends AccountService {

    public AccountServiceMock() {
        super(null, null);
    }

    @Override
    public String authenticate(String username, String password) throws AuthenticationFailedException {
        if(username.equals("student") && password.equals("password")) {
            return "student";
        } else if(username.equals("faculty") && password.equals("password")) {
            return "faculty";
        } else {
            throw new AuthenticationFailedException();
        }
    }

    @Override
    public void register(String firstName, String lastName, String age, String username, String password) throws RegistrationFailedException {
    }

    @Override
    public List<Course> getCourses() {
        return super.getCourses();
    }

    @Override
    public String getAccountName() {
        return super.getAccountName();
    }
}
