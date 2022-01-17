package main.test.java.mocks.services;

import main.java.exceptions.CreationFailedException;
import main.java.exceptions.EnrollmentFailedException;
import main.java.exceptions.MissingAccountException;
import main.java.exceptions.RegistrationFailedException;
import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.models.User;
import main.java.repositories.CourseRepository;
import main.java.repositories.EnrollmentRepository;
import main.java.repositories.UserRepository;
import main.java.services.AccountService;
import main.java.structures.List;

public class AccountServiceMock extends AccountService {

    private String flag;

    public AccountServiceMock(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, String flag) {
        super(userRepository, courseRepository, enrollmentRepository);
        this.flag = flag;
    }

    @Override
    public void assign(Course course) throws CreationFailedException {
    }

    @Override
    public String authenticate(String username, String password) throws MissingAccountException {
        if(this.flag.equals(username)) {
            return flag;
        } else {
            throw new MissingAccountException();
        }
    }

    @Override
    public void register(String firstName, String lastName, String email, long dateOfBirth, String username, String password, int mealPlanTier, String major) throws RegistrationFailedException {
    }

    @Override
    public void enroll(Course course) throws EnrollmentFailedException {
    }

    @Override
    public void unenroll(Course course) throws EnrollmentFailedException {
    }

    @Override
    public List<Course> getCourses() {
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

        Course course = new Course();
        course.setCourseId(1);
        course.setName("course1");
        course.setCourseId(1);
        course.setProfessor(faculty);
        course.addStudent(student);

        List<Course> courses = new List<>();
        courses.add(course);

        return courses;
    }

    @Override
    public String getUserName() {
        return "Mockery";
    }

    @Override
    public boolean isUserFaculty() {
        return this.flag.equals("faculty");
    }

    @Override
    public User getUser() {
        if(this.flag.equals("faculty")) {
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
    }
}
