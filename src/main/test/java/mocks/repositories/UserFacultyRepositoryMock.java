package main.test.java.mocks.repositories;

import main.java.exceptions.NoSuchUserException;
import main.java.models.User;
import main.java.repositories.UserRepository;
import main.test.java.mocks.models.FacultyMock;

public class UserFacultyRepositoryMock extends UserRepository {

    @Override
    public User getUser(String username, String password) throws NoSuchUserException {
        if(username.equalsIgnoreCase("username") && password.equalsIgnoreCase("password")) {
            return new FacultyMock();
        } else {
            throw new NoSuchUserException();
        }
    }
}
