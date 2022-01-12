package main.test.java.mocks.models;

import main.java.models.User;

public class FacultyMock extends User {

    @Override
    public String getType() {
        return "faculty";
    }
}
