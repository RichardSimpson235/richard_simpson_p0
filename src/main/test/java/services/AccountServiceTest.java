package main.test.java.services;

import main.java.exceptions.NoSuchUserException;
import main.java.exceptions.RegistrationFailedException;
import main.java.services.AccountService;
import main.test.java.mocks.repositories.UserFacultyRepositoryMock;
import main.test.java.mocks.repositories.UserRepositoryAccountRegisterFailMock;
import main.test.java.mocks.repositories.UserStudentRepositoryMock;
import main.test.java.mocks.structures.ContextServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceTest {

    private ContextServiceMock contextServiceMock;
    private UserFacultyRepositoryMock userFacultyRepositoryMock;
    private UserStudentRepositoryMock userStudentRepositoryMock;

    @BeforeEach
    public void init() {
        this.contextServiceMock = new ContextServiceMock();
        this.userFacultyRepositoryMock = new UserFacultyRepositoryMock();
        this.userStudentRepositoryMock = new UserStudentRepositoryMock();
    }

    @Test
    public void testFacultyAuthenticateSuccess() {
        AccountService service = new AccountService(this.contextServiceMock, this.userFacultyRepositoryMock);
        try {
            String s = service.authenticate("username", "password");

            assertEquals(s, "faculty");
        } catch (NoSuchUserException e) {
            assert false;
        }
    }

    @Test
    public void testStudentAuthenticateSuccess() {
        AccountService service = new AccountService(this.contextServiceMock, this.userStudentRepositoryMock);
        try {
            String s = service.authenticate("username", "password");

            assertEquals(s, "student");
        } catch (NoSuchUserException e) {
            assert false;
        }
    }

    @Test
    public void testFacultyAuthenticationFailure() {
        AccountService service = new AccountService(this.contextServiceMock, this.userFacultyRepositoryMock);
        assertThrows(NoSuchUserException.class, () -> {
            service.authenticate("username", "pass");
        });
    }

    @Test
    public void testStudentAuthenticationFailure() {
        AccountService service = new AccountService(this.contextServiceMock, this.userStudentRepositoryMock);
        assertThrows(NoSuchUserException.class, () -> {
            service.authenticate("username", "pass");
        });
    }

    @Test
    public void testRegisterAccountFailure() {
        UserRepositoryAccountRegisterFailMock mockRepo = new UserRepositoryAccountRegisterFailMock();
        AccountService service = new AccountService(this.contextServiceMock, mockRepo);

        assertThrows(RegistrationFailedException.class, () -> {
            service.register("firstname", "lastname", "23", "username", "lastname");
        });
    }
}
