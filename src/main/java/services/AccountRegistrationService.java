package main.java.services;

import com.sun.deploy.association.RegisterFailedException;
import main.java.repositories.UserRepository;

public class AccountRegistrationService {

    private final UserRepository userRepository;

    public AccountRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String firstName, String lastName, int age, String username, String password) throws RegisterFailedException {
        this.userRepository.createUser(firstName, lastName, age, username, password);
    }
}
