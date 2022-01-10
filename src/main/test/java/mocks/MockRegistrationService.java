package main.test.java.mocks;

import main.java.services.RegistrationService;

public class MockRegistrationService extends RegistrationService {

    @Override
    public void register(String firstName, String lastName, String age, String username, String password) {}
}
