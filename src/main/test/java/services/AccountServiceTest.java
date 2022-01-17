package main.test.java.services;

import main.java.exceptions.MissingAccountException;
import main.java.exceptions.RegistrationFailedException;
import main.java.models.Course;
import main.java.services.AccountService;
import main.java.structures.List;
import main.test.java.mocks.repositories.CourseRepositoryMock;
import main.test.java.mocks.repositories.EnrollmentRepositoryMock;
import main.test.java.mocks.repositories.UserRepositoryMock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private UserRepositoryMock userFacultyRepositoryMockSuccess;
    private UserRepositoryMock userFacultyRepositoryMockFailure;
    private UserRepositoryMock userFacultyRepositoryMockSQLException;
    private UserRepositoryMock userStudentRepositoryMockSuccess;
    private UserRepositoryMock userStudentRepositoryMockFailure;
    private UserRepositoryMock userStudentRepositoryMockSQLException;
    private CourseRepositoryMock courseRepositoryMockSuccess;
    private EnrollmentRepositoryMock enrollmentRepositorySuccess;

    @BeforeEach
    public void init() {
        this.userFacultyRepositoryMockSuccess = new UserRepositoryMock(null, "success", "faculty");
        this.userFacultyRepositoryMockFailure = new UserRepositoryMock(null, "failure", "faculty");
        this.userFacultyRepositoryMockSQLException = new UserRepositoryMock(null, "throws", "faculty");
        this.userStudentRepositoryMockSuccess = new UserRepositoryMock(null, "success", "student");
        this.userStudentRepositoryMockFailure = new UserRepositoryMock(null, "failure", "student");
        this.userStudentRepositoryMockSQLException = new UserRepositoryMock(null, "throws", "student");
        this.courseRepositoryMockSuccess = new CourseRepositoryMock(null, "success");
        this.enrollmentRepositorySuccess = new EnrollmentRepositoryMock(null, "success");
    }

    @Test
    public void testAuthenticateFacultySuccess() {

        AccountService service = new AccountService(this.userFacultyRepositoryMockSuccess,
                                                    this.courseRepositoryMockSuccess,
                                                    this.enrollmentRepositorySuccess);

        try {
            String s = service.authenticate("username", "password");
            assertEquals(s, "faculty");
        } catch (MissingAccountException e) {
            assert false;
        }

        List<Course> courses = service.getCourses();

        assertEquals(courses.size(), 1);

        assertEquals(courses.get(0).getCourseId(), 1);
    }

    @Test
    public void testAuthenticateFacultyFail() {
        AccountService service = new AccountService(this.userFacultyRepositoryMockFailure,null, null);

        assertThrows(MissingAccountException.class, () -> {
            service.authenticate("username", "password");
        });
    }

    @Test
    public void testAuthenticateFacultySQLException() {
        AccountService service = new AccountService(this.userFacultyRepositoryMockSQLException,null, null);

        assertThrows(MissingAccountException.class, () -> {
            service.authenticate("username", "password");
        });
    }

    @Test
    public void testAuthenticateStudentSuccess() {

        AccountService service = new AccountService(this.userStudentRepositoryMockSuccess,
                                                    this.courseRepositoryMockSuccess,
                                                    this.enrollmentRepositorySuccess);

        try {
            String s = service.authenticate("username", "password");
            assertEquals(s, "student");
        } catch (MissingAccountException e) {
            assert false;
        }

        List<Course> courses = service.getCourses();

        assertEquals(courses.size(), 1);

        assertEquals(courses.get(0).getCourseId(), 1);
    }

    @Test
    public void testAuthenticateStudentFail() {
        AccountService service = new AccountService(this.userStudentRepositoryMockFailure,null, null);

        assertThrows(MissingAccountException.class, () -> {
            service.authenticate("username", "password");
        });
    }

    @Test
    public void testAuthenticateStudentSQLException() {
        AccountService service = new AccountService(this.userStudentRepositoryMockSQLException,null, null);

        assertThrows(MissingAccountException.class, () -> {
            service.authenticate("username", "password");
        });
    }

    @Test
    public void testRegisterSuccess() {
        AccountService service = new AccountService(this.userStudentRepositoryMockSuccess, null, null);

        assertDoesNotThrow(() -> {
            service.register(
                    "first",
                    "last",
                    "first@gmail.com",
                    0L,
                    "username",
                    "password",
                    0,
                    "DEPT"
            );
        });
    }

    @Test
    public void testRegisterFailure() {
        AccountService service = new AccountService(this.userStudentRepositoryMockFailure, null, null);

        assertThrows(RegistrationFailedException.class, () -> {
            service.register(
                    "first",
                    "last",
                    "first@gmail.com",
                    0L,
                    "username",
                    "password",
                    0,
                    "DEPT"
            );
        });
    }

    @Test
    public void testRegisterSQLException() {
        AccountService service = new AccountService(this.userStudentRepositoryMockSQLException, null, null);

        assertThrows(RegistrationFailedException.class, () -> {
            service.register(
                    "first",
                    "last",
                    "first@gmail.com",
                    0L,
                    "username",
                    "password",
                    0,
                    "DEPT"
            );
        });
    }
}
