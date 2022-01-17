package main.test.java.mocks.repositories;

import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.repositories.EnrollmentRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class EnrollmentRepositoryMock extends EnrollmentRepository {

    private final String flag;

    public EnrollmentRepositoryMock(Connection connection, String flag) {
        super(connection);
        this.flag = flag;
    }

    @Override
    public boolean assign(Faculty faculty, Course course) throws SQLException {
        return super.assign(faculty, course);
    }

    @Override
    public boolean create(Student student, Course course) throws SQLException {
        return super.create(student, course);
    }

    @Override
    public boolean delete(Student student, Course course) throws SQLException {
        return super.delete(student, course);
    }
}
