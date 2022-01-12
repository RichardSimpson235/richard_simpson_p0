package main.java.services;

import main.java.exceptions.NoSuchUserException;
import main.java.models.User;
import main.java.repositories.UserRepository;
import main.java.structures.Context;

public class LoginService {

    private ContextService contextService;
    private UserRepository userRepository;

    public LoginService(ContextService contextService, UserRepository userRepository) {
        this.contextService = contextService;
        this.userRepository = userRepository;
    }

    public String authenticate(String username, String password) throws NoSuchUserException {
        User user = this.userRepository.getUser(username, password);
        Context context = new Context(user);
        this.contextService.setContext(context);

        return user.getType();
    }
}
