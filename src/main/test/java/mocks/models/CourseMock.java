package main.test.java.mocks.models;

import main.java.models.Course;

public class CourseMock extends Course {

    @Override
    public String toString() {
        return "mock course";
    }

    @Override
    public boolean isValidField(String input) {
        return true;
    }
}
