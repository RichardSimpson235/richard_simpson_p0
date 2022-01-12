package main.test.java.services;

import main.java.exceptions.NoSuchUserException;
import main.java.services.LoginService;
import main.test.java.mocks.structures.ContextServiceMock;
import main.test.java.mocks.repositories.UserFacultyRepositoryMock;
import main.test.java.mocks.repositories.UserStudentRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginServiceTest {

    private ContextServiceMock contextService;
    private UserFacultyRepositoryMock facultyRepo;
    private UserStudentRepositoryMock studentRepo;
    private LoginService service;

    @BeforeEach
    public void init() {
        contextService = new ContextServiceMock();
        facultyRepo = new UserFacultyRepositoryMock();
        studentRepo = new UserStudentRepositoryMock();
    }


    @Test
    public void testAuthenticationForFaculty() {
        service = new LoginService(contextService, facultyRepo);

        try {
            String s = service.authenticate("username", "password");

            assertEquals(s, "faculty");
        } catch (NoSuchUserException e) {
            assert false;
        }
    }

    @Test
    public void testAuthenticationForStudent() {
        service = new LoginService(contextService, studentRepo);

        try {
            String s = service.authenticate("username", "password");

            assertEquals(s, "student");
        } catch (NoSuchUserException e) {
            assert false;
        }
    }

    @Test
    public void testAuthenticationFailForFaculty() {
        service = new LoginService(contextService, facultyRepo);

        assertThrows(NoSuchUserException.class, () -> {
            service.authenticate("username", "pass");
        });
    }

    @Test
    public void testAuthenticationFailForStudent() {
        service = new LoginService(contextService, studentRepo);

        assertThrows(NoSuchUserException.class, () -> {
            service.authenticate("username", "pass");
        });
    }
}
