package main.test.java.mocks.repositories;

import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.models.User;
import main.java.repositories.CourseRepository;
import main.java.structures.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

public class CourseRepositoryMock extends CourseRepository {

    private final String flag;

    public CourseRepositoryMock(Connection connection, String flag) {
        super(connection);

        this.flag = flag;
    }

    @Override
    public boolean create(Course course) throws SQLException {

        if(flag.equals("success"))
        {
            assertEquals(course.getName(), "name");
            assertEquals(course.getDescription(), "description");
            assertEquals(course.getEnrollmentStartDate(), 0L);
            assertEquals(course.getEnrollmentEndDate(), 0L);
            assertEquals(course.getCredits(), 3);

            return true;
        } else if(flag.equals("failure")) {
            return false;
        } else {
            throw new SQLException();
        }
    }

    @Override
    public boolean delete(Course course) throws SQLException {
        if(flag.equals("success")) {
            return true;
        } else if (flag.equals("failure")) {
            return false;
        } else {
            throw new SQLException();
        }
    }

    @Override
    public boolean edit(Course course) throws SQLException {
        if(flag.equals("success")) {
            assertEquals(course.getName(), "different name");

            return true;
        } else if (flag.equals("failure")) {
            return false;
        } else {
            throw new SQLException();
        }
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        if(flag.equals("success")) {
            List<Course> courses = new List<>();

            Faculty faculty1 = new Faculty();
            faculty1.setUserId(1);
            faculty1.setFirstName("first");
            faculty1.setLastName("last");
            faculty1.setEmail("first@gmail.com");
            faculty1.setDateOfBirth(0L);
            faculty1.setUsername("username");
            faculty1.setPassword("password");
            faculty1.setSalary(10);
            faculty1.setDepartment("DEPT");

            Faculty faculty2 = new Faculty();
            faculty2.setUserId(2);
            faculty2.setFirstName("second");
            faculty2.setLastName("last");
            faculty2.setEmail("second@gmail.com");
            faculty2.setDateOfBirth(0L);
            faculty2.setUsername("username");
            faculty2.setPassword("password");
            faculty2.setSalary(10);
            faculty2.setDepartment("DEPT");

            Student student1 = new Student();
            student1.setUserId(3);
            student1.setFirstName("first");
            student1.setLastName("last");
            student1.setEmail("first@gmail.com");
            student1.setDateOfBirth(0L);
            student1.setUsername("username");
            student1.setPassword("password");
            student1.setMealPlanTier(10);
            student1.setMajor("DEPT");

            Student student2 = new Student();
            student2.setUserId(4);
            student2.setFirstName("second");
            student2.setLastName("last");
            student2.setEmail("second@gmail.com");
            student2.setDateOfBirth(0L);
            student2.setUsername("username");
            student2.setPassword("password");
            student2.setMealPlanTier(10);
            student2.setMajor("DEPT");

            Course course1 = new Course();
            course1.setCourseId(1);
            course1.setName("course1");
            course1.setCourseId(1);
            course1.setProfessor(faculty1);
            course1.addStudent(student1);

            Course course2 = new Course();
            course2.setCourseId(2);
            course2.setName("course2");
            course2.setCourseId(2);
            course2.setProfessor(faculty2);
            course2.addStudent(student2);

            courses.add(course1);
            courses.add(course2);

            return courses;
        } else if(flag.equals("failure")) {
            return null;
        } else {
            throw new SQLException();
        }
    }
}
