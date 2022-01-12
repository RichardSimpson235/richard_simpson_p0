package main.test.java.mocks.models;

import main.java.models.User;

public class StudentMock extends User {

    @Override
    public String getType() {
        return "student";
    }
}
