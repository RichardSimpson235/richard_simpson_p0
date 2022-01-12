package main.test.java.mocks.repositories;

import main.java.exceptions.RegistrationFailedException;
import main.java.repositories.UserRepository;

public class UserRepositoryAccountRegisterFailMock extends UserRepository {

    @Override
    public void createUser(String firstName, String lastName, int age, String username, String password) throws RegistrationFailedException {
        throw new RegistrationFailedException();
    }
}
