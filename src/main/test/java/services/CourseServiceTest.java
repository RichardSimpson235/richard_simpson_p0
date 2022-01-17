package main.test.java.services;

import main.java.exceptions.CourseModificationException;
import main.java.exceptions.CreationFailedException;
import main.java.exceptions.DeletionFailedException;
import main.java.exceptions.InvalidFieldException;
import main.java.models.Course;
import main.java.services.CourseService;
import main.test.java.mocks.repositories.CourseRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseServiceTest {

    private CourseRepositoryMock courseRepositoryMockSuccess;
    private CourseRepositoryMock courseRepositoryMockFailure;
    private CourseRepositoryMock courseRepositoryMockThrowsSQLException;

    @BeforeEach
    public void init() {
        this.courseRepositoryMockSuccess = new CourseRepositoryMock(null, "success");
        this.courseRepositoryMockFailure = new CourseRepositoryMock(null, "failure");
        this.courseRepositoryMockThrowsSQLException = new CourseRepositoryMock(null, "throws");
    }

    @Test
    public void testCreateCourseSuccess() {
        String name = "name";
        String description = "description";
        long enrollmentStart = 0L;
        long enrollmentEnd = 0L;
        int credits = 3;

        CourseService service = new CourseService(this.courseRepositoryMockSuccess);

        assertDoesNotThrow(() -> {
            service.createCourse(name, description, enrollmentStart, enrollmentEnd, credits);
        });
    }

    @Test
    public void testCreateCourseFailure() {
        String name = "name";
        String description = "description";
        long enrollmentStart = 0L;
        long enrollmentEnd = 0L;
        int credits = 3;

        CourseService service = new CourseService(this.courseRepositoryMockFailure);

        assertThrows(CreationFailedException.class, () -> {
            service.createCourse(name, description, enrollmentStart, enrollmentEnd, credits);
        });
    }

    @Test
    public void testCreateCourseSQLException() {
        String name = "name";
        String description = "description";
        long enrollmentStart = 0L;
        long enrollmentEnd = 0L;
        int credits = 3;

        CourseService service = new CourseService(this.courseRepositoryMockThrowsSQLException);

        assertThrows(CreationFailedException.class, () -> {
            service.createCourse(name, description, enrollmentStart, enrollmentEnd, credits);
        });
    }

    @Test
    public void testDeleteCourseSuccess() {
        Course course = new Course();
        course.setName("name");
        course.setDescription("description");
        course.setEnrollmentStartDate(0L);
        course.setEnrollmentEndDate(0L);
        course.setCredits(3);

        CourseService service = new CourseService(this.courseRepositoryMockSuccess);
        service.selectCourse(course);

        assertDoesNotThrow(() -> {
            service.deleteCourse();
        });
    }

    @Test
    public void testDeleteCourseFailure() {
        Course course = new Course();
        course.setName("name");
        course.setDescription("description");
        course.setEnrollmentStartDate(0L);
        course.setEnrollmentEndDate(0L);
        course.setCredits(3);

        CourseService service = new CourseService(this.courseRepositoryMockFailure);
        service.selectCourse(course);

        assertThrows(DeletionFailedException.class, () -> {
            service.deleteCourse();
        });
    }

    @Test
    public void testDeleteCourseSQLException() {
        Course course = new Course();
        course.setName("name");
        course.setDescription("description");
        course.setEnrollmentStartDate(0L);
        course.setEnrollmentEndDate(0L);
        course.setCredits(3);

        CourseService service = new CourseService(this.courseRepositoryMockThrowsSQLException);
        service.selectCourse(course);

        assertThrows(DeletionFailedException.class, () -> {
            service.deleteCourse();
        });
    }

    @Test
    public void testEditCourseSuccess() {
        Course course = new Course();
        course.setName("name");
        course.setDescription("description");
        course.setEnrollmentStartDate(0L);
        course.setEnrollmentEndDate(0L);
        course.setCredits(3);

        CourseService service = new CourseService(this.courseRepositoryMockSuccess);
        service.selectCourse(course);

        assertDoesNotThrow(() -> {
            service.editCourse("name", "different name");
        });
    }

    @Test
    public void testEditCourseFailure() {
        Course course = new Course();
        course.setName("name");
        course.setDescription("description");
        course.setEnrollmentStartDate(0L);
        course.setEnrollmentEndDate(0L);
        course.setCredits(3);

        CourseService service = new CourseService(this.courseRepositoryMockFailure);
        service.selectCourse(course);

        assertThrows(InvalidFieldException.class, () -> {
            service.editCourse("blah", "different name");
        });
    }

    @Test
    public void testEditCourseSQLException() {
        Course course = new Course();
        course.setName("name");
        course.setDescription("description");
        course.setEnrollmentStartDate(0L);
        course.setEnrollmentEndDate(0L);
        course.setCredits(3);

        CourseService service = new CourseService(this.courseRepositoryMockThrowsSQLException);
        service.selectCourse(course);

        assertThrows(CourseModificationException.class, () -> {
            service.editCourse("name", "different name");
        });
    }
}
