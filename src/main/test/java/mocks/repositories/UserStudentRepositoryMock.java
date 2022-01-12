package main.test.java.mocks.repositories;

import main.java.exceptions.AuthenticationFailedException;
import main.java.models.User;
import main.java.repositories.UserRepository;
import main.test.java.mocks.models.StudentMock;

public class UserStudentRepositoryMock extends UserRepository {

    @Override
    public User getUser(String username, String password) throws AuthenticationFailedException {

        if(username.equalsIgnoreCase("username") && password.equalsIgnoreCase("password")) {
            return new StudentMock();
        } else {
            throw new AuthenticationFailedException();
        }
    }
}
