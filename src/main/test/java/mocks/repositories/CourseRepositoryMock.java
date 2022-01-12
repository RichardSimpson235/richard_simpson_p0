package main.test.java.mocks.repositories;

import main.java.exceptions.FailedEnrollmentException;
import main.java.models.Course;
import main.java.models.User;
import main.java.repositories.CourseRepository;

public class CourseRepositoryMock extends CourseRepository {

    @Override
    public void enrollUser(User user, Course course) throws FailedEnrollmentException {
        throw new FailedEnrollmentException();
    }

    @Override
    public void unenrollUser(User user, Course course) throws FailedEnrollmentException {
        throw new FailedEnrollmentException();
    }
}
