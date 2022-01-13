package main.test.java.services;

import main.java.exceptions.FailedEnrollmentException;
import main.java.services.CourseService;
import main.test.java.mocks.repositories.CourseRepositoryMock;
import main.test.java.mocks.services.ContextServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseServiceTest {

    private CourseRepositoryMock courseRepositoryMock;
    private ContextServiceMock contextServiceMock;

    @BeforeEach
    public void init() {
        this.courseRepositoryMock = new CourseRepositoryMock();
        this.contextServiceMock = new ContextServiceMock();
    }

    @Test
    public void testEnrollmentFail() {
        CourseService service = new CourseService(this.contextServiceMock, this.courseRepositoryMock);

        assertThrows(FailedEnrollmentException.class, () -> {
            service.enrollUser();
        });
    }

    @Test
    public void testUnenrollmentFail() {
        CourseService service = new CourseService(this.contextServiceMock, this.courseRepositoryMock);

        assertThrows(FailedEnrollmentException.class, () -> {
            service.unenrollUser();
        });
    }
}
