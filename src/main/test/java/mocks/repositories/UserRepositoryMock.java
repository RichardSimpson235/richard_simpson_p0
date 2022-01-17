package main.test.java.mocks.repositories;

import main.java.exceptions.MissingAccountException;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.models.User;
import main.java.repositories.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryMock extends UserRepository {

    private final String flag;
    private final String type;

    public UserRepositoryMock(Connection connection, String flag, String type) {
        super(connection);
        this.flag = flag;
        this.type = type;
    }

    @Override
    public boolean create(Student student) throws SQLException {
        if(flag.equals("success"))
        {
            assertEquals(student.getFirstName(), "first");
            assertEquals(student.getLastName(), "last");
            assertEquals(student.getEmail(), "first@gmail.com");
            assertEquals(student.getDateOfBirth(), 0L);
            assertEquals(student.getUsername(), "username");
            assertEquals(student.getPassword(), "password");
            assertEquals(student.getMealPlanTier(), 0);
            assertEquals(student.getMajor(), "DEPT");

            return true;
        } else if(flag.equals("failure")) {
            return false;
        } else {
            throw new SQLException();
        }
    }

    @Override
    public User get(String username, String password) throws SQLException {
        if(flag.equals("success")) {
            if(this.type.equals("faculty")) {
                Faculty faculty = new Faculty();
                faculty.setUserId(1);
                faculty.setFirstName("first");
                faculty.setLastName("last");
                faculty.setEmail("first@gmail.com");
                faculty.setDateOfBirth(0L);
                faculty.setUsername("username");
                faculty.setPassword("password");
                faculty.setSalary(10);
                faculty.setDepartment("DEPT");

                return faculty;
            } else {
                Student student = new Student();
                student.setUserId(3);
                student.setFirstName("first");
                student.setLastName("last");
                student.setEmail("first@gmail.com");
                student.setDateOfBirth(0L);
                student.setUsername("username");
                student.setPassword("password");
                student.setMealPlanTier(10);
                student.setMajor("DEPT");

                return student;
            }
        } else if(flag.equals("failure")) {
            return null;
        } else {
            throw new SQLException();
        }
    }
}
