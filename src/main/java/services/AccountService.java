package main.java.services;

import main.java.exceptions.AuthenticationFailedException;
import main.java.exceptions.RegistrationFailedException;
import main.java.models.Course;
import main.java.models.User;
import main.java.repositories.UserRepository;
import main.java.structures.Context;
import main.java.structures.List;

public class AccountService {

    private final ContextService contextService;
    private final UserRepository userRepository;
    private User user;

    public AccountService(ContextService contextService, UserRepository userRepository) {
        this.contextService = contextService;
        this.userRepository = userRepository;
    }

    public String authenticate(String username, String password) throws AuthenticationFailedException {
        this.user = this.userRepository.getUser(username, password);
        this.contextService.setContext(new Context(user));

        return user.getType();
    }

    public void register(String firstName, String lastName, String age, String username, String password) throws RegistrationFailedException {
        this.userRepository.createUser(firstName, lastName, age, username, password);
    }

    public List<Course> getCourses() {
        return this.user.getCourses();
    }

    public String getAccountName() {
        return this.user.getName();
    }

    public void viewCourse(Course course) {
        this.contextService.getContext().course = course;
    }
}
